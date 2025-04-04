package org.example.erp.features.inventory.domain.useCase.warehouse

import org.example.erp.features.inventory.domain.repository.WarehouseRepo

class DeleteWarehouseUseCase(
    private val repository: WarehouseRepo
) {
    suspend operator fun invoke(code: String) = repository.deleteWarehouse(code)
}