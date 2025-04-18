package org.example.erp.features.inventory.domain.useCase.unitOfMeasures

import org.example.erp.features.inventory.domain.repository.UnitOfMeasureRepo

class SyncUnitsOfMeasureUseCase(
    val repository: UnitOfMeasureRepo
) {
    operator fun invoke() = repository.syncUnitsOfMeasure()
}