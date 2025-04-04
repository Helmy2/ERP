package org.example.erp.features.inventory.domain.useCase.unitOfMeasures

import org.example.erp.features.inventory.domain.repository.UnitsOfMeasureRepo

class GetUnitOfMeasuresByCodeUseCase(
    private val repository: UnitsOfMeasureRepo
) {
    suspend operator fun invoke(code: String) = repository.getUnitOfMeasure(code)
}