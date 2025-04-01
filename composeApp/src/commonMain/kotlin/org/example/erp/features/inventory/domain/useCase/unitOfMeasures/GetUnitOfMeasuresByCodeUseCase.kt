package org.example.erp.features.inventory.domain.useCase.unitOfMeasures

import org.example.erp.features.inventory.domain.repository.InventoryReps

class GetUnitOfMeasuresByCodeUseCase(
    private val repository: InventoryReps
) {
    suspend operator fun invoke(code: String) = repository.getUnitOfMeasure(code)
}