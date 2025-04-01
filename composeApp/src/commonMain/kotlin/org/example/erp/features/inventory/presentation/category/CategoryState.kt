package org.example.erp.features.inventory.presentation.category

import org.example.erp.features.inventory.domain.entity.Category

data class CategoryState(
    val loading: Boolean = true,
    val categories: List<Category> = emptyList(),
    val selectedCategory: Category? = null,
    val code: String = "",
    val name: String = "",
    val parentCategoryCode: String? = null,
    val parentCategory: Category? = null,
    val isParentCategoryOpen: Boolean = false,
    val getDisplayNameForUser: suspend (String) -> String
) {
    val isNew: Boolean get() = selectedCategory == null
}