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
import org.example.erp.features.inventory.data.local.dao.WarehouseDao
import org.example.erp.features.inventory.data.model.WarehouseResponse
import org.example.erp.features.inventory.domain.entity.Warehouses
import org.example.erp.features.inventory.domain.mapper.toDomain
import org.example.erp.features.inventory.domain.repository.WarehouseRepo

class WarehouseRepoImpl(
    private val supabaseClient: SupabaseClient,
    private val warehouseDao: WarehouseDao,
    private val dispatcher: CoroutineDispatcher
): WarehouseRepo {
    override suspend fun getWarehouse(code: String): Result<Warehouses> = withContext(dispatcher) {
        runCatching {
            warehouseDao.getByCode(code)?.toDomain() ?: throw Exception("Warehouse not found")
        }
    }

    @OptIn(SupabaseExperimental::class)
    override fun syncWarehouse(): Result<Unit> =
        runCatching {
            CoroutineScope(dispatcher).launch {
                supabaseClient.from(
                    SupabaseConfig.WAREHOUSE
                ).selectAsFlow(
                    WarehouseResponse::id
                ).onEach {
                    performDataComparison(
                        localData = warehouseDao.getAll(""),
                        remoteData = it,
                        keySelector = { response -> response.id },
                        areItemsDifferent = { local, remote ->
                            local.code != remote.code || local.name != remote.name || local.capacity != remote.capacity || local.location != remote.location
                        },
                    ).apply {
                        warehouseDao.delete(toDelete)
                        warehouseDao.insert(toInsert)
                    }
                }.catch {
                    println("Exception in syncWarehouse: $it")
                }.launchIn(this)
            }
        }

    override suspend fun getAllWarehouse(query: String): Result<List<Warehouses>> = runCatching {
        warehouseDao.getAll(query).map { it.toDomain() }
    }

    override suspend fun createWarehouse(
        code: String, name: String, capacity: Long?, location: String
    ): Result<Unit> = withContext(dispatcher) {
        runCatching {
            supabaseClient.from(SupabaseConfig.WAREHOUSE).insert(
                buildJsonObject {
                    put(WarehouseResponse::code.name, code)
                    put(WarehouseResponse::name.name, name)
                    put(WarehouseResponse::capacity.name, capacity)
                    put(WarehouseResponse::location.name, location)
                })
            Unit
        }
    }

    override suspend fun updateWarehouse(
        id: String, code: String, name: String, capacity: Long?, location: String
    ): Result<Unit> = withContext(dispatcher) {
        runCatching {
            supabaseClient.from(SupabaseConfig.WAREHOUSE).update(
                buildJsonObject {
                    put(WarehouseResponse::code.name, code)
                    put(WarehouseResponse::name.name, name)
                    put(WarehouseResponse::capacity.name, capacity)
                    put(WarehouseResponse::location.name, location)
                }) {
                filter {
                    WarehouseResponse::id eq id
                }
            }
            Unit
        }
    }

    override suspend fun deleteWarehouse(code: String): Result<Unit> = withContext(dispatcher) {
        runCatching {
            supabaseClient.from(SupabaseConfig.WAREHOUSE)
                .delete { filter { WarehouseResponse::code eq code } }
            Unit
        }
    }
}