package org.example.erp.features.inventory.domain.useCase.unitOfMeasures

import org.example.erp.features.inventory.domain.repository.UnitOfMeasureRepo

class CreateUnitOfMeasureUseCase(
    private val repository: UnitOfMeasureRepo
) {
    suspend operator fun invoke(
        code: String,
        name: String,
        description: String
    ) = repository.createUnitOfMeasure(code, name, description)
}