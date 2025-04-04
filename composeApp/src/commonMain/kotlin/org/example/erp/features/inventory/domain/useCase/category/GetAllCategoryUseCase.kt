package org.example.erp.features.inventory.domain.useCase.category

import org.example.erp.features.inventory.domain.repository.CategoryRepo

class GetAllCategoryUseCase(
    private val repository: CategoryRepo
) {
    suspend operator fun invoke(query: String) = repository.getCategories(query)
}