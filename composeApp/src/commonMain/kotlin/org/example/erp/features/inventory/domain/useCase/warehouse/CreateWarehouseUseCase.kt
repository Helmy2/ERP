package org.example.erp.features.inventory.domain.useCase.warehouse

import org.example.erp.features.inventory.domain.repository.WarehouseRepo

class CreateWarehouseUseCase(
    private val repository: WarehouseRepo
) {
    suspend operator fun invoke(
        code: String,
        name: String,
        capacity: Long?,
        location: String
    ) = repository.createWarehouse(code, name, capacity, location)
}