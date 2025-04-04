package org.example.erp.features.inventory.domain.useCase.unitOfMeasures

import org.example.erp.features.inventory.domain.repository.UnitsOfMeasureRepo

class GetAllUnitsOfMeasureUseCase(
    private val repository: UnitsOfMeasureRepo
) {
    suspend operator fun invoke(query: String) = repository.getAllUnitsOfMeasure(query)
}