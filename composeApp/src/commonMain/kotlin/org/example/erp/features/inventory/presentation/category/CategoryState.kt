package org.example.erp.features.inventory.presentation.category

import org.example.erp.features.inventory.domain.entity.Category
import org.example.erp.features.user.domain.entity.User

data class CategoryState(
    val loading: Boolean = true,
    val categories: List<Category> = emptyList(),
    val selectedCategory: Category? = null,
    val code: String = "",
    val name: String = "",
    val parentCategoryCode: String? = null,

    val parentCategory: Category? = null,
    val isParentCategoryOpen: Boolean = false,
    val getUserById: suspend (String) -> Result<User>
) {
    val isNew: Boolean get() = selectedCategory == null
    val forbiddenItemCodes = getAllDescendantCodesIncludingSelfRecursive(selectedCategory, categories) + code
}

fun getAllDescendantCodesIncludingSelfRecursive(
    selectedCategory: Category?,
    categories: List<Category>
): List<String> {
    val codes = mutableSetOf<String>()
    if (selectedCategory != null) {
        codes.add(selectedCategory.code)
        val children = categories.filter { it.parentCategory?.id == selectedCategory.id }
        codes.addAll(children.map { it.code })
        children.forEach { child ->
            codes.addAll(getAllDescendantCodesIncludingSelfRecursive(child, categories))
        }
    }
    return codes.toList()
}
