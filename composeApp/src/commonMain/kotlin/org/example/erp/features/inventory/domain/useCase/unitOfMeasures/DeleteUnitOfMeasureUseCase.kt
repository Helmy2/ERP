package org.example.erp.features.inventory.domain.useCase.unitOfMeasures

import org.example.erp.features.inventory.domain.repository.UnitOfMeasureRepo

class DeleteUnitOfMeasureUseCase(
    private val repository: UnitOfMeasureRepo
) {
    suspend operator fun invoke(code: String) = repository.deleteUnitOfMeasure(code)
}