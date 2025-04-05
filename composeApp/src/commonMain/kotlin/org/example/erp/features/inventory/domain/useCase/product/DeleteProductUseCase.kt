package org.example.erp.features.inventory.domain.useCase.product

import org.example.erp.features.inventory.domain.repository.ProductRepo

class DeleteProductUseCase(
    private val productRepo: ProductRepo
){
    suspend operator fun invoke(code: String): Result<Unit> = productRepo.deleteProduct(code)
}