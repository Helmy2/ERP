package org.example.erp.features.inventory.domain.useCase.category

import org.example.erp.features.inventory.domain.repository.CategoryRepo

class GetCategoryByIdUseCase(
    private val categoryRepo: CategoryRepo
) {
    suspend operator fun invoke(id: String) = categoryRepo.getCategoryById(id)
}