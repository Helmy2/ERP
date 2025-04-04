package org.example.erp.features.inventory.domain.useCase.warehouse

import org.example.erp.features.inventory.domain.repository.WarehouseRepo

class UpdateWarehouseUseCase(
    private val repository: WarehouseRepo
) {
    suspend operator fun invoke(
        id: String,
        code: String,
        name: String,
        capacity: Long?,
        location: String
    ) = repository.updateWarehouse(id, code, name, capacity, location)
}