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
    override fun getAllUnitsOfMeasure(): Flow<Result<List<UnitsOfMeasure>>> = channelFlow {
        launch {
            supabaseClient.from(
                UNIT_OF_MEASURE
            ).selectAsFlow(
                UnitsOfMeasureResponse::id
            ).onEach {
                performDataComparison(
                    localData = unitsOfMeasureDao.getAll().first(),
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
                trySend(Result.failure(it))
            }.launchIn(this)
        }
        launch {
            unitsOfMeasureDao.getAll().map {
                it.map { entity ->
                    entity.toDomain()
                }
            }.catch {
                trySend(Result.failure(it))
            }.collectLatest {
                trySend(Result.success(it))
            }
        }
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
    override fun getAllWarehouse(): Flow<Result<List<Warehouses>>> = channelFlow {
        launch {
            supabaseClient.from(
                WAREHOUSE
            ).selectAsFlow(
                WarehouseResponse::id
            ).onEach {
                performDataComparison(
                    localData = warehouseDao.getAll().first(),
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
                trySend(Result.failure(it))
            }.launchIn(this)
        }
        launch {
            warehouseDao.getAll().map {
                it.map { entity ->
                    entity.toDomain()
                }
            }.catch {
                trySend(Result.failure(it))
            }.collectLatest {
                trySend(Result.success(it))
            }
        }
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

    @OptIn(SupabaseExperimental::class)
    override fun getCategories(): Flow<Result<List<Category>>> = channelFlow {
        launch {
            supabaseClient.from(
                CATEGORY
            ).selectAsFlow(
                CategoryResponse::id
            ).onEach {
                performDataComparison(
                    localData = categoryDao.getAll().first(),
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
                trySend(Result.failure(it))
            }.launchIn(this)
        }
        launch {
            categoryDao.getAll().map { list ->
                list.map {
                    it.toDomain(
                        children = getCategoriesChildren(it, list),
                        parentCategory = getCategoryParent(it, list),
                    )
                }
            }.catch {
                trySend(Result.failure(it))
            }.collectLatest {
                trySend(Result.success(it))
            }
        }
    }

    private fun getCategoriesChildren(
        category: CategoryResponse, categories: List<CategoryResponse>
    ): List<Category> {
        val children = categories.filter { it.parentCategoryId == category.id }
        return children.map {
            it.toDomain(
                if (children.isEmpty()) emptyList() else getCategoriesChildren(it, categories),
                if (category.parentCategoryId == null) null else getCategoryParent(
                    it, categories
                )
            )
        }
    }

    private fun getCategoryParent(
        category: CategoryResponse, categories: List<CategoryResponse>
    ): Category? {
        val parent = categories.firstOrNull { it.id == category.parentCategoryId }
        return parent?.toDomain(
            getCategoriesChildren(parent, categories), getCategoryParent(parent, categories)
        )
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