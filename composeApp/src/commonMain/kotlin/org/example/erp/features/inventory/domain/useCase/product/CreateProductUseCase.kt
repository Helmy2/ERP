package org.example.erp.features.inventory.domain.useCase.product

import org.example.erp.features.inventory.domain.repository.ProductRepo

class CreateProductUseCase(
    private val productRepo: ProductRepo
){
    suspend operator fun invoke(
        code: String,
        name: String,
        sku: String,
        description: String,
        unitPrice: Double,
        costPrice: Double,
        categoryId: String,
        unitOfMeasureId: String
    ): Result<Unit> = productRepo.createProduct(code, name, sku, description, unitPrice, costPrice, categoryId, unitOfMeasureId)
}