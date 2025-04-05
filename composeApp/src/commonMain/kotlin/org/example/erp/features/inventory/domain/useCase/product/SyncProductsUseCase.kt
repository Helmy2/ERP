package org.example.erp.features.inventory.domain.useCase.product

import org.example.erp.features.inventory.domain.repository.ProductRepo


class SyncProductsUseCase(
    private val productRepo: ProductRepo
){
    operator fun invoke(): Result<Unit> = productRepo.syncProduct()
}

