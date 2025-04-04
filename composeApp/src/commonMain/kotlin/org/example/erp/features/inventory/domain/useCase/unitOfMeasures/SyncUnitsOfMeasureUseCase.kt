package org.example.erp.features.inventory.domain.useCase.unitOfMeasures

import org.example.erp.features.inventory.domain.repository.UnitsOfMeasureRepo

class SyncUnitsOfMeasureUseCase(
    val repository: UnitsOfMeasureRepo
) {
    operator fun invoke() = repository.syncUnitsOfMeasure()
}