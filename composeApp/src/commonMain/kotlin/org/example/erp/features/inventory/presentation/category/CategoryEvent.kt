package org.example.erp.features.inventory.presentation.category

sealed interface CategoryEvent {
    data object CreateCategory : CategoryEvent
    data object UpdateCategory : CategoryEvent
    data object DeleteCategory : CategoryEvent

    data class SearchCategory(val code: String) : CategoryEvent
    data class UpdateCode(val code: String) : CategoryEvent
    data class UpdateName(val name: String) : CategoryEvent
    data class UpdateParentCategoryCode(val code: String?) : CategoryEvent
    data class UpdateIsParentCategoryOpen(val open: Boolean) : CategoryEvent
    data class UpdateQuery(val query: String) : CategoryEvent
    data class UpdateIsQueryActive(val isQueryActive: Boolean) : CategoryEvent
    data class Search(val query: String) : CategoryEvent
}