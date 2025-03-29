package org.example.erp.features.inventory.presentation.unitOfMeasures

import org.example.erp.features.inventory.domain.entity.UnitsOfMeasure

data class UnitOfMeasuresState(
    val loading: Boolean = true,
    val unitsOfMeasureList: List<UnitsOfMeasure> = emptyList(),
    val selectedUnitOfMeasure: UnitsOfMeasure? = null,
    val code: String = "",
    val name: String = "",
    val description: String = "",
    val getDisplayNameForUser: suspend (String) -> String
) {
    val isNewUnitOfMeasure: Boolean get() = selectedUnitOfMeasure == null
}