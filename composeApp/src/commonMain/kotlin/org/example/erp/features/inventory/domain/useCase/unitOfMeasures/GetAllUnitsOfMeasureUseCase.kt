package org.example.erp.features.inventory.domain.useCase.unitOfMeasures

import org.example.erp.features.inventory.domain.repository.InventoryReps

class GetAllUnitsOfMeasureUseCase(
    private val repository: InventoryReps
) {
    suspend operator fun invoke(query: String) = repository.getAllUnitsOfMeasure(query)
}