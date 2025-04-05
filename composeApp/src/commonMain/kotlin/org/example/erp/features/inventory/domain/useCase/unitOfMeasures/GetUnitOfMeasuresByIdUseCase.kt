package org.example.erp.features.inventory.domain.useCase.unitOfMeasures

import org.example.erp.features.inventory.domain.repository.UnitOfMeasureRepo

class GetUnitOfMeasuresByIdUseCase(
    val repo: UnitOfMeasureRepo
) {
    suspend operator fun invoke(id: String) = repo.getUnitOfMeasuresById(id)
}