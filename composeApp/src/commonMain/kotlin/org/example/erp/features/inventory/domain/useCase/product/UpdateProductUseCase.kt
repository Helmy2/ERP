package org.example.erp.features.inventory.domain.useCase.product

import org.example.erp.features.inventory.domain.repository.ProductRepo

class UpdateProductUseCase(
    private val productRepo: ProductRepo
){
    suspend operator fun invoke(
        id: String,
        code: String,
        name: String,
        sku: String,
        description: String,
        unitPrice: Double,
        costPrice: Double,
        categoryId: String,
        unitOfMeasureId: String
    ): Result<Unit> = productRepo.updateProduct(id, code, name, sku, description, unitPrice, costPrice, categoryId, unitOfMeasureId)
}