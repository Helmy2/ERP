package org.example.erp.features.inventory.domain.useCase.product

import org.example.erp.features.inventory.domain.entity.Product
import org.example.erp.features.inventory.domain.repository.ProductRepo

class GetProductByIdUseCase(
    private val productRepo: ProductRepo
){
    suspend operator fun invoke(id: String): Result<Product> = productRepo.getProductById(id)
}