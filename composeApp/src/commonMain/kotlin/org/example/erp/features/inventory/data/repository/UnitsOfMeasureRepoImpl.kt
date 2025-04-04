package org.example.erp.features.inventory.data.repository

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.example.erp.core.util.SupabaseConfig
import org.example.erp.core.util.performDataComparison
import org.example.erp.features.inventory.data.local.dao.UnitsOfMeasureDao
import org.example.erp.features.inventory.data.model.UnitsOfMeasureResponse
import org.example.erp.features.inventory.domain.entity.UnitsOfMeasure
import org.example.erp.features.inventory.domain.mapper.toDomain
import org.example.erp.features.inventory.domain.repository.UnitsOfMeasureRepo

class UnitsOfMeasureRepoImpl(
    private val supabaseClient: SupabaseClient,
    private val unitsOfMeasureDao: UnitsOfMeasureDao,
    private val dispatcher: CoroutineDispatcher
): UnitsOfMeasureRepo {
    override suspend fun getUnitOfMeasure(code: String): Result<UnitsOfMeasure> =
        withContext(dispatcher) {
            runCatching {
                unitsOfMeasureDao.getByCode(code).toDomain()
            }
        }

    @OptIn(SupabaseExperimental::class)
    override fun syncUnitsOfMeasure(): Result<Unit> = runCatching {
        CoroutineScope(dispatcher).launch {
            supabaseClient.from(
                SupabaseConfig.UNIT_OF_MEASURE
            ).selectAsFlow(
                UnitsOfMeasureResponse::id
            ).onEach {
                performDataComparison(
                    localData = unitsOfMeasureDao.getAll(""),
                    remoteData = it,
                    keySelector = { response -> response.id },
                    areItemsDifferent = { local, remote ->
                        local.code != remote.code || local.name != remote.name || local.description != remote.description
                    },
                ).apply {
                    unitsOfMeasureDao.delete(toDelete)
                    unitsOfMeasureDao.insert(toInsert)
                }
            }.catch {
                println("Exception in syncUnitsOfMeasure: $it")
            }.launchIn(this)
        }
    }


    override suspend fun getAllUnitsOfMeasure(
        query: String
    ): Result<List<UnitsOfMeasure>> = runCatching {
        unitsOfMeasureDao.getAll(query).map { it.toDomain() }
    }


    override suspend fun createUnitOfMeasure(
        code: String, name: String, description: String
    ): Result<Unit> = withContext(dispatcher) {
        runCatching {
            supabaseClient.from(SupabaseConfig.UNIT_OF_MEASURE).insert(
                buildJsonObject {
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
            supabaseClient.from(SupabaseConfig.UNIT_OF_MEASURE).update(
                buildJsonObject {
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
            supabaseClient.from(SupabaseConfig.UNIT_OF_MEASURE)
                .delete { filter { UnitsOfMeasureResponse::code eq code } }
            Unit
        }
    }
}