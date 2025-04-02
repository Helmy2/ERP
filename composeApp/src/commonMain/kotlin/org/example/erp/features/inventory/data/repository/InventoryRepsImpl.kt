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
import org.example.erp.core.util.SupabaseConfig.CATEGORY
import org.example.erp.core.util.SupabaseConfig.UNIT_OF_MEASURE
import org.example.erp.core.util.SupabaseConfig.WAREHOUSE
import org.example.erp.core.util.performDataComparison
import org.example.erp.features.inventory.data.local.dao.CategoryDao
import org.example.erp.features.inventory.data.local.dao.UnitsOfMeasureDao
import org.example.erp.features.inventory.data.local.dao.WarehouseDao
import org.example.erp.features.inventory.data.model.CategoryResponse
import org.example.erp.features.inventory.data.model.UnitsOfMeasureResponse
import org.example.erp.features.inventory.data.model.WarehouseResponse
import org.example.erp.features.inventory.domain.entity.Category
import org.example.erp.features.inventory.domain.entity.UnitsOfMeasure
import org.example.erp.features.inventory.domain.entity.Warehouses
import org.example.erp.features.inventory.domain.mapper.toDomain
import org.example.erp.features.inventory.domain.repository.InventoryReps

class InventoryRepsImpl(
    private val supabaseClient: SupabaseClient,
    private val unitsOfMeasureDao: UnitsOfMeasureDao,
    private val warehouseDao: WarehouseDao,
    private val categoryDao: CategoryDao,
    private val dispatcher: CoroutineDispatcher
) : InventoryReps {

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
                UNIT_OF_MEASURE
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
            supabaseClient.from(UNIT_OF_MEASURE).insert(
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
            supabaseClient.from(UNIT_OF_MEASURE).update(
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
            supabaseClient.from(UNIT_OF_MEASURE)
                .delete { filter { UnitsOfMeasureResponse::code eq code } }
            Unit
        }
    }

    override suspend fun getWarehouse(code: String): Result<Warehouses> = withContext(dispatcher) {
        runCatching {
            warehouseDao.getByCode(code).toDomain()
        }
    }

    @OptIn(SupabaseExperimental::class)
    override fun syncWarehouse(): Result<Unit> =
        runCatching {
            CoroutineScope(dispatcher).launch {
                supabaseClient.from(
                    WAREHOUSE
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
            supabaseClient.from(WAREHOUSE).insert(
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
            supabaseClient.from(WAREHOUSE).update(
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
            supabaseClient.from(WAREHOUSE).delete { filter { WarehouseResponse::code eq code } }
            Unit
        }
    }

    override suspend fun getCategory(code: String): Result<Category> = runCatching {
        val response = categoryDao.getByCode(code)
        response.toDomain(
            children = categoryDao.getChildren(response.id).map { it.toDomain(emptyList(), null) },
            parentCategory = if (response.parentCategoryId == null) null
            else categoryDao.getById(response.parentCategoryId).toDomain(emptyList(), null),
        )
    }

    override suspend fun getCategories(query: String): Result<List<Category>> = runCatching {
        categoryDao.getAll(query).map { it.toDomain(emptyList(), null) }
    }

    @OptIn(SupabaseExperimental::class)
    override fun syncCategories(): Result<Unit> = runCatching {
        CoroutineScope(dispatcher).launch {
            supabaseClient.from(
                CATEGORY
            ).selectAsFlow(
                CategoryResponse::id
            ).onEach {
                performDataComparison(
                    localData = categoryDao.getAll(""),
                    remoteData = it,
                    keySelector = { response -> response.id },
                    areItemsDifferent = { local, remote ->
                        local.code != remote.code || local.name != remote.name || local.parentCategoryId != remote.parentCategoryId
                    },
                ).apply {
                    categoryDao.delete(toDelete)
                    categoryDao.insert(toInsert)
                }
            }.catch {
                println("Exception in syncCategories: $it")
            }.launchIn(this)
        }
    }

    override suspend fun createCategory(
        code: String, name: String, parentCategoryId: String?
    ): Result<Unit> = withContext(dispatcher) {
        runCatching {
            supabaseClient.from(CATEGORY).insert(
                buildJsonObject {
                    put(CategoryResponse::code.name, code)
                    put(CategoryResponse::name.name, name)
                    put("parent_category_id", parentCategoryId)
                })
            Unit
        }
    }

    override suspend fun updateCategory(
        id: String, code: String, name: String, parentCategoryId: String?
    ): Result<Unit> = withContext(dispatcher) {
        runCatching {
            supabaseClient.from(CATEGORY).update(
                buildJsonObject {
                    put(CategoryResponse::code.name, code)
                    put(CategoryResponse::name.name, name)
                    put("parent_category_id", parentCategoryId)
                }) {
                filter {
                    CategoryResponse::id eq id
                }
            }
            Unit
        }
    }

    override suspend fun deleteCategory(code: String): Result<Unit> = withContext(dispatcher) {
        runCatching {
            supabaseClient.from(CATEGORY).delete { filter { CategoryResponse::code eq code } }
            Unit
        }
    }
}