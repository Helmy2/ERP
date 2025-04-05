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
import org.example.erp.core.util.performDataComparison
import org.example.erp.features.inventory.data.local.dao.CategoryDao
import org.example.erp.features.inventory.data.model.CategoryResponse
import org.example.erp.features.inventory.domain.entity.Category
import org.example.erp.features.inventory.domain.mapper.toDomain
import org.example.erp.features.inventory.domain.repository.CategoryRepo

class CategoryRepoImpl(
    private val supabaseClient: SupabaseClient,
    private val categoryDao: CategoryDao,
    private val dispatcher: CoroutineDispatcher
) : CategoryRepo {

    override suspend fun getCategoryByCode(code: String): Result<Category> = runCatching {
        val response = categoryDao.getByCode(code) ?: throw Exception("Category not found")
        val parentCategory =
            if (response.parentCategoryId == null) null else categoryDao.getById(response.parentCategoryId)
                ?.toDomain(emptyList(), null)

        response.toDomain(
            children = categoryDao.getChildren(response.id).map { it.toDomain(emptyList(), null) },
            parentCategory = parentCategory,
        )
    }

    override suspend fun getCategoryById(id: String): Result<Category> = runCatching {
        val response = categoryDao.getById(id) ?: throw Exception("Category not found")
        val parentCategory =
            if (response.parentCategoryId == null) null else categoryDao.getById(response.parentCategoryId)
                ?.toDomain(emptyList(), null)


        response.toDomain(
            children = categoryDao.getChildren(response.id).map { it.toDomain(emptyList(), null) },
            parentCategory = parentCategory,
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

