package org.example.erp.features.inventory.domain.useCase.unitOfMeasures

import org.example.erp.features.inventory.domain.repository.UnitsOfMeasureRepo

class CreateUnitOfMeasureUseCase(
    private val repository: UnitsOfMeasureRepo
) {
    suspend operator fun invoke(
        code: String,
        name: String,
        description: String
    ) = repository.createUnitOfMeasure(code, name, description)
}