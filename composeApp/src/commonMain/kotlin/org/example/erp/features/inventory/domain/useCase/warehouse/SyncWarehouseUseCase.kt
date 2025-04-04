package org.example.erp.features.inventory.domain.useCase.warehouse

import org.example.erp.features.inventory.domain.repository.WarehouseRepo

class SyncWarehouseUseCase(
    private val repository: WarehouseRepo
) {
    operator fun invoke() = repository.syncWarehouse()
}