package org.example.erp.features.inventory.domain.useCase.unitOfMeasures

import org.example.erp.features.inventory.domain.repository.UnitsOfMeasureRepo

class DeleteUnitOfMeasureUseCase(
    private val repository: UnitsOfMeasureRepo
) {
    suspend operator fun invoke(code: String) = repository.deleteUnitOfMeasure(code)
}