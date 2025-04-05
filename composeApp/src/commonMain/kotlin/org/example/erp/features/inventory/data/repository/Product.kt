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
import org.example.erp.features.inventory.data.local.dao.ProductDao
import org.example.erp.features.inventory.data.model.ProductResponse
import org.example.erp.features.inventory.domain.entity.Product
import org.example.erp.features.inventory.domain.mapper.toDomain
import org.example.erp.features.inventory.domain.repository.ProductRepo
import org.example.erp.features.inventory.domain.useCase.category.GetCategoryByCodeUseCase
import org.example.erp.features.inventory.domain.useCase.unitOfMeasures.GetUnitOfMeasuresByCodeUseCase

class ProductRepoImpl(
    private val supabaseClient: SupabaseClient,
    private val productDao: ProductDao,
    private val getCategoryByCodeUseCase: GetCategoryByCodeUseCase,
    private val getUnitsOfMeasureByCodeUseCase: GetUnitOfMeasuresByCodeUseCase,
    private val dispatcher: CoroutineDispatcher
) : ProductRepo {

    @OptIn(SupabaseExperimental::class)
    override fun syncProduct(): Result<Unit> = runCatching {
        CoroutineScope(dispatcher).launch {
            supabaseClient.from(
                SupabaseConfig.PRODUCT_TABLE
            ).selectAsFlow(
                ProductResponse::id
            ).onEach {
                performDataComparison(
                    localData = productDao.getAll(""),
                    remoteData = it,
                    keySelector = { response -> response.id },
                    areItemsDifferent = { local, remote ->
                        local.code != remote.code || local.name != remote.name || local.categoryId != remote.categoryId || local.unitOfMeasureId != remote.unitOfMeasureId || local.description != remote.description || local.sku != remote.sku || local.unitPrice != remote.unitPrice || local.costPrice != remote.costPrice
                    },
                ).apply {
                    productDao.delete(toDelete)
                    productDao.insert(toInsert)
                }
            }.catch {
                println("Exception in syncProduct: $it")
            }.launchIn(this)
        }
    }

    override suspend fun getProduct(code: String): Result<Product> = runCatching {
        val response = productDao.getByCode(code)
        response.toDomain(
            category = getCategoryByCodeUseCase(
                response.categoryId ?: throw Exception("Category not found")
            ).getOrThrow(),
            unitOfMeasure = getUnitsOfMeasureByCodeUseCase(response.unitOfMeasureId).getOrThrow()
        )
    }

    override suspend fun getAllProduct(query: String): Result<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun createProduct(
        code: String,
        name: String,
        sku: String,
        description: String,
        unitPrice: Double,
        costPrice: Double,
        categoryId: String,
        unitOfMeasureId: String
    ): Result<Unit> = withContext(dispatcher) {
        runCatching {
            supabaseClient.from(SupabaseConfig.PRODUCT_TABLE).insert(
                buildJsonObject {
                    put(SupabaseConfig.CODE_ROW, code)
                    put(SupabaseConfig.NAME_ROW, name)
                    put(SupabaseConfig.SKU_ROW, sku)
                    put(SupabaseConfig.UNIT_OF_MEASURE_ID_ROW, unitOfMeasureId)
                    put(SupabaseConfig.DESCRIPTION_ROW, description)
                    put(SupabaseConfig.UNIT_PRICE_ROW, unitPrice)
                    put(SupabaseConfig.COST_PRICE_ROW, costPrice)
                    put(SupabaseConfig.CATEGORY_ID_ROW, categoryId)
                })
            Unit
        }
    }

    override suspend fun updateProduct(
        id: String,
        code: String,
        name: String,
        sku: String,
        description: String,
        unitPrice: Double,
        costPrice: Double,
        categoryId: String,
        unitOfMeasureId: String
    ): Result<Unit> = withContext(dispatcher) {
        runCatching {
            supabaseClient.from(SupabaseConfig.PRODUCT_TABLE).update(
                buildJsonObject {
                    put(SupabaseConfig.CODE_ROW, code)
                    put(SupabaseConfig.NAME_ROW, name)
                    put(SupabaseConfig.SKU_ROW, sku)
                    put(SupabaseConfig.UNIT_OF_MEASURE_ID_ROW, unitOfMeasureId)
                    put(SupabaseConfig.DESCRIPTION_ROW, description)
                    put(SupabaseConfig.UNIT_PRICE_ROW, unitPrice)
                    put(SupabaseConfig.COST_PRICE_ROW, costPrice)
                    put(SupabaseConfig.CATEGORY_ID_ROW, categoryId)
                }) {
                filter {
                    ProductResponse::id eq id
                }
            }
            Unit
        }
    }

    override suspend fun deleteProduct(code: String): Result<Unit> = withContext(dispatcher) {
        runCatching {
            supabaseClient.from(SupabaseConfig.PRODUCT_TABLE)
                .delete { filter { ProductResponse::code eq code } }
            Unit
        }
    }
}