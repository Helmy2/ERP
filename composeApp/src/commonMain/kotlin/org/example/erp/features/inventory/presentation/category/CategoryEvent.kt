package org.example.erp.features.inventory.presentation.category

import org.example.erp.features.inventory.domain.entity.Category

sealed interface CategoryEvent {
    data class CreateCategory(val name: String) : CategoryEvent
    data class UpdateCategory(val name: String) : CategoryEvent
    data class DeleteCategory(val name: String) : CategoryEvent

    data class SearchCategory(val code: String) : CategoryEvent
    data class UpdateCode(val code: String) : CategoryEvent
    data class UpdateName(val name: String) : CategoryEvent
    data class UpdateParentCategory(val category: Category) : CategoryEvent
}