package org.example.erp.features.inventory.presentation.unitOfMeasures

sealed interface UnitOfMeasuresEvent {
    data object CreateUnitOfMeasure : UnitOfMeasuresEvent
    data object UpdateUnitOfMeasure : UnitOfMeasuresEvent
    data object DeleteUnitOfMeasure: UnitOfMeasuresEvent

    data class SearchUnitOfMeasure(val code: String): UnitOfMeasuresEvent
    data class UpdateCode(val code: String) : UnitOfMeasuresEvent
    data class UpdateName(val name: String) : UnitOfMeasuresEvent
    data class UpdateDescription(val description: String) : UnitOfMeasuresEvent
}