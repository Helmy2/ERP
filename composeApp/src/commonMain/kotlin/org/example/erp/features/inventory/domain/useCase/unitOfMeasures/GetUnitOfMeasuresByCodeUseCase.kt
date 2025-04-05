package org.example.erp.features.inventory.domain.useCase.unitOfMeasures

import org.example.erp.features.inventory.domain.repository.UnitOfMeasureRepo

class GetUnitOfMeasuresByCodeUseCase(
    private val repository: UnitOfMeasureRepo
) {
    suspend operator fun invoke(code: String) = repository.getUnitOfMeasureByCode(code)
}