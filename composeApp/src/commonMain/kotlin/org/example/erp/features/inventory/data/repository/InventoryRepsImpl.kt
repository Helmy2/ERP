package org.example.erp.features.inventory.data.repository

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.erp.core.util.SupabaseConfig.UNIT_OF_MEASURE
import org.example.erp.core.util.performDataComparison
import org.example.erp.features.inventory.data.local.dao.InventoryDao
import org.example.erp.features.inventory.data.model.UnitsOfMeasureResponse
import org.example.erp.features.inventory.domain.entity.UnitsOfMeasure
import org.example.erp.features.inventory.domain.mapper.toDomain
import org.example.erp.features.inventory.domain.repository.InventoryReps

class InventoryRepsImpl(
    private val supabaseClient: SupabaseClient,
    private val inventoryDao: InventoryDao,
    private val dispatcher: CoroutineDispatcher
) : InventoryReps {

    @OptIn(SupabaseExperimental::class)
    override fun getAllUnitsOfMeasure(): Flow<List<UnitsOfMeasure>> = channelFlow {
        launch {
            supabaseClient.from(
                UNIT_OF_MEASURE
            ).selectAsFlow(
                UnitsOfMeasureResponse::id
            ).onEach {
                println("InventoryRepsImpl: $it")
                performDataComparison(
                    localData = inventoryDao.getAll().first(),
                    remoteData = it,
                    keySelector = { response -> response.id },
                    areItemsDifferent = { local, remote ->
                        local.code != remote.code ||
                                local.name != remote.name ||
                                local.description != remote.description
                    },
                ).apply {
                    println(
                        "InventoryRepsImpl: toDelete: $toDelete, toInsert: $toInsert"
                    )
                    inventoryDao.delete(toDelete)
                    inventoryDao.insert(toInsert)
                }
            }.catch {
                println("Exception in getAllUnitsOfMeasure: $it")
            }.launchIn(this)
        }
        launch {
            inventoryDao.getAll().map {
                it.map { entity ->
                    entity.toDomain()
                }
            }.collectLatest {
                trySend(it)
            }
        }
    }


    override suspend fun createUnitOfMeasure(
        code: String, name: String, description: String
    ): Result<Unit> = withContext(dispatcher) {
        runCatching {
            supabaseClient.from(UNIT_OF_MEASURE).insert(
                buildMap {
                    put(UnitsOfMeasureResponse::code.name, code)
                    put(UnitsOfMeasureResponse::name.name, name)
                    put(UnitsOfMeasureResponse::description.name, description)
                })
            Unit
        }
    }

    override suspend fun updateUnitOfMeasure(
        id: String, code: String, name: String, description: String
    ): Result<Unit> = withContext(dispatcher) {
        runCatching {
            supabaseClient.from(UNIT_OF_MEASURE).update(
                buildMap {
                    put(UnitsOfMeasureResponse::code.name, code)
                    put(UnitsOfMeasureResponse::name.name, name)
                    put(UnitsOfMeasureResponse::description.name, description)
                }) {
                filter {
                    UnitsOfMeasureResponse::id eq id
                }
            }
            Unit
        }
    }

    override suspend fun deleteUnitOfMeasure(code: String): Result<Unit> = withContext(dispatcher) {
        runCatching {
            supabaseClient.from(UNIT_OF_MEASURE)
                .delete { filter { UnitsOfMeasureResponse::code eq code } }
            Unit
        }
    }
}

