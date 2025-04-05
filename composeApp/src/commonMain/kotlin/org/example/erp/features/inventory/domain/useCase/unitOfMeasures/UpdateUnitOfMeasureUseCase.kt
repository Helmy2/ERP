package org.example.erp.features.inventory.domain.useCase.unitOfMeasures

import org.example.erp.features.inventory.domain.repository.UnitOfMeasureRepo

class UpdateUnitOfMeasureUseCase(
    private val repository: UnitOfMeasureRepo
) {
    suspend operator fun invoke(
        id: String,
        code: String,
        name: String,
        description: String
    ) = repository.updateUnitOfMeasure(id, code, name, description)
}