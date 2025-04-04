package org.example.erp.features.inventory.domain.useCase.category

import org.example.erp.features.inventory.domain.repository.CategoryRepo

class DeleteCategoryUseCase(
    private val repository: CategoryRepo
) {
    suspend operator fun invoke(id: String) = repository.deleteCategory(id)
}