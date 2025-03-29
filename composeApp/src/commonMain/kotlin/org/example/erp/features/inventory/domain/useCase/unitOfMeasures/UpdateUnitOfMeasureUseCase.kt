package org.example.erp.features.inventory.domain.useCase.unitOfMeasures

import org.example.erp.features.inventory.domain.repository.InventoryReps

class UpdateUnitOfMeasureUseCase(
    private val repository: InventoryReps
) {
    suspend operator fun invoke(
        id: String,
        code: String,
        name: String,
        description: String
    ) = repository.updateUnitOfMeasure(id, code, name, description)
}