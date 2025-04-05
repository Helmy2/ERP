package org.example.erp.features.inventory.domain.repository

import org.example.erp.features.inventory.domain.entity.Product

interface ProductRepo {
    fun syncProduct(): Result<Unit>

    suspend fun getProduct(code: String): Result<Product>

    suspend fun getAllProduct(query: String): Result<List<Product>>

    suspend fun createProduct(
        code: String,
        name: String,
        sku: String,
        description: String,
        unitPrice: Double,
        costPrice: Double,
        categoryId: String?,
        unitOfMeasureId: String?
    ): Result<Unit>

    suspend fun updateProduct(
        id: String,
        code: String,
        name: String,
        sku: String,
        description: String,
        unitPrice: Double,
        costPrice: Double,
        categoryId: String?,
        unitOfMeasureId: String?
    ): Result<Unit>

    suspend fun deleteProduct(code: String): Result<Unit>
}