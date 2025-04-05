package org.example.erp.features.inventory.domain.useCase.product

import org.example.erp.features.inventory.domain.entity.Product
import org.example.erp.features.inventory.domain.repository.ProductRepo

class GetAllProductUseCase(
    private val productRepo: ProductRepo
){
    suspend operator fun invoke(query: String): Result<List<Product>> = productRepo.getAllProduct(query)
}