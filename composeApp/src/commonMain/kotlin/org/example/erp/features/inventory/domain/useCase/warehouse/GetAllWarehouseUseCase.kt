package org.example.erp.features.inventory.domain.useCase.warehouse

import org.example.erp.features.inventory.domain.repository.WarehouseRepo

class GetAllWarehouseUseCase(
    private val repository: WarehouseRepo
) {
    suspend operator fun invoke(query: String) = repository.getAllWarehouse(query)
}