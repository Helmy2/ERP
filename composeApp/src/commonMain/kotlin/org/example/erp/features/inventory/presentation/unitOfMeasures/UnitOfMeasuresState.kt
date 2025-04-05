package org.example.erp.features.inventory.presentation.unitOfMeasures

import org.example.erp.features.inventory.domain.entity.UnitOfMeasure
import org.example.erp.features.user.domain.entity.User

data class UnitOfMeasuresState(
    val loading: Boolean = false,
    val searchResults: List<UnitOfMeasure> = emptyList(),
    val selectedUnitOfMeasure: UnitOfMeasure? = null,
    val code: String = "",
    val name: String = "",
    val description: String = "",
    val query: String = "",
    val isQueryActive: Boolean = false,
    val getUserById: suspend (String) -> Result<User>
) {
    val isNew: Boolean get() = selectedUnitOfMeasure == null
}