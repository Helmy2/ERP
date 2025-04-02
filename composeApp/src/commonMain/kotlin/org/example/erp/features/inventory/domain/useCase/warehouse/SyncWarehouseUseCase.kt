package org.example.erp.features.inventory.domain.useCase.warehouse

import org.example.erp.features.inventory.domain.repository.InventoryReps

class SyncWarehouseUseCase(
    private val repository: InventoryReps
) {
    operator fun invoke() = repository.syncWarehouse()
}