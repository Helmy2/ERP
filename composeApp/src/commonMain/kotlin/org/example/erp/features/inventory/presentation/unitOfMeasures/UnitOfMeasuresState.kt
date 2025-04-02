package org.example.erp.features.inventory.presentation.unitOfMeasures

import org.example.erp.features.inventory.domain.entity.UnitsOfMeasure
import org.example.erp.features.user.domain.entity.User

data class UnitOfMeasuresState(
    val loading: Boolean = true,
    val unitsOfMeasureList: List<UnitsOfMeasure> = emptyList(),
    val selectedUnitOfMeasure: UnitsOfMeasure? = null,
    val code: String = "",
    val name: String = "",
    val description: String = "",
    val getUserById: suspend (String) -> Result<User>
) {
    val isNew: Boolean get() = selectedUnitOfMeasure == null
}