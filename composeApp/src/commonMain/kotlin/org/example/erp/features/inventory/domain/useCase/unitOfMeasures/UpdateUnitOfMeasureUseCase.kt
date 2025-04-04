package org.example.erp.features.inventory.domain.useCase.unitOfMeasures

import org.example.erp.features.inventory.domain.repository.UnitsOfMeasureRepo

class UpdateUnitOfMeasureUseCase(
    private val repository: UnitsOfMeasureRepo
) {
    suspend operator fun invoke(
        id: String,
        code: String,
        name: String,
        description: String
    ) = repository.updateUnitOfMeasure(id, code, name, description)
}