package org.example.erp.features.inventory.domain.useCase.product

import org.example.erp.features.inventory.domain.entity.Product
import org.example.erp.features.inventory.domain.repository.ProductRepo

class GetProductByCodeUseCase(
    private val productRepo: ProductRepo
){
    suspend operator fun invoke(code: String): Result<Product> = productRepo.getProduct(code)
}