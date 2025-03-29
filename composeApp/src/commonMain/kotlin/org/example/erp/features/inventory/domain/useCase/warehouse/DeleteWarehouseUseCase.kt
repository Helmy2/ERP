package org.example.erp.features.inventory.domain.useCase.warehouse

import org.example.erp.features.inventory.domain.repository.InventoryReps

class DeleteWarehouseUseCase(
    private val repository: InventoryReps
) {
    suspend operator fun invoke(code: String) = repository.deleteWarehouse(code)
}