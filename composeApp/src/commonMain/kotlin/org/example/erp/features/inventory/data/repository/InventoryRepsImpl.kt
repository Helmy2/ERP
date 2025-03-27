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
import org.example.erp.core.domain.entity.DataChange
import org.example.erp.core.util.DISPLAY_NAME_KEY
import org.example.erp.core.util.SupabaseConfig.UNIT_OF_MEASURE
import org.example.erp.core.util.SupabaseConfig.USER_ROLE
import org.example.erp.features.inventory.data.exception.UnitOfMeasureNotFoundException
import org.example.erp.features.inventory.data.local.dao.InventoryDao
import org.example.erp.features.inventory.data.model.UnitsOfMeasureResponse
import org.example.erp.features.inventory.domain.entity.UnitsOfMeasure
import org.example.erp.features.inventory.domain.mapper.toDomain
import org.example.erp.features.inventory.domain.repository.InventoryReps
import org.example.erp.features.user.domain.entity.User

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
                val data = performDataComparison(
                    localData = inventoryDao.getAll().first(), remoteData = it
                )
                inventoryDao.delete(data.toDelete)
                inventoryDao.insert(
                    data.toInsert.map { response ->
                        response.copy(
                            createdBy = if (response.createdBy != null) getUserById(response.createdBy) else "",
                            updatedBy = if (response.updatedBy != null) getUserById(response.updatedBy) else "",
                        )
                    },
                )
                inventoryDao.insert(
                    data.toUpdate.map { response ->
                        response.copy(
                            createdBy = if (response.createdBy != null) getUserById(response.createdBy) else "",
                            updatedBy = if (response.updatedBy != null) getUserById(response.updatedBy) else "",
                        )
                    },
                )
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

    private fun performDataComparison(
        localData: List<UnitsOfMeasureResponse>, remoteData: List<UnitsOfMeasureResponse>
    ): DataChange<UnitsOfMeasureResponse> {
        // Create maps for efficient lookups
        val localMap = localData.associateBy { it.id }
        val remoteMap = remoteData.associateBy { it.id }

        // Find elements that need to be deleted (present in local but not in remote)
        val toDelete = localData.filter { !remoteMap.containsKey(it.id) }

        // Find elements that need to be inserted (present in remote but not in local)
        val toInsert = remoteData.filter { !localMap.containsKey(it.id) }

        // Find elements that need to be updated (present in both but different content)
        val toUpdate = remoteData.filter { remoteItem ->
            localMap[remoteItem.id]?.let { localItem ->
                // Compare all fields except auto-managed timestamps
                remoteItem.code != localItem.code || remoteItem.name != localItem.name || remoteItem.description != localItem.description
            } ?: false
        }

        return DataChange(
            toDelete = toDelete, toInsert = toInsert, toUpdate = toUpdate
        )
    }


    private suspend fun getUserById(id: String): String {
        val name = supabaseClient.from(USER_ROLE).select {
            filter { User::id eq id }
        }.decodeSingle<Map<String, String?>>()

        return name[DISPLAY_NAME_KEY] ?: ""
    }

    override suspend fun getUnitOfMeasureByCode(code: String): Result<UnitsOfMeasure> =
        withContext(dispatcher) {
            runCatching {
                val result = supabaseClient.from(UNIT_OF_MEASURE)
                    .select { filter { UnitsOfMeasureResponse::code eq code } }
                    .decodeSingleOrNull<UnitsOfMeasureResponse>()

                val unit = result?.toDomain()?.copy(
                    createdBy = getUserById(result.createdBy!!),
                    updatedBy = result.updatedBy?.let { userId -> getUserById(userId) })

                unit ?: throw UnitOfMeasureNotFoundException()
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
