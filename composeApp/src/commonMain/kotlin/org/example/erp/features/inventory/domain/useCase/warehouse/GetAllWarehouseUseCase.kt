package org.example.erp.features.inventory.domain.useCase.warehouse

import org.example.erp.features.inventory.domain.repository.InventoryReps

class GetAllWarehouseUseCase(
    private val repository: InventoryReps
) {
    operator fun invoke() = repository.getAllWarehouse()

}