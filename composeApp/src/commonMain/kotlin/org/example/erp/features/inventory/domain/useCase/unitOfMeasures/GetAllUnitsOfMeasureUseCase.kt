package org.example.erp.features.inventory.domain.useCase.unitOfMeasures

import org.example.erp.features.inventory.domain.repository.UnitOfMeasureRepo

class GetAllUnitsOfMeasureUseCase(
    private val repository: UnitOfMeasureRepo
) {
    suspend operator fun invoke(query: String) = repository.getAllUnitsOfMeasure(query)
}