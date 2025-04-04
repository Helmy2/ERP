package org.example.erp.features.inventory.domain.useCase.category

import org.example.erp.features.inventory.domain.repository.CategoryRepo

class CreateCategoryUseCase(
    private val repository: CategoryRepo
) {
    suspend operator fun invoke(
        code: String,
        name: String,
        parentCategoryId: String?
    ) = repository.createCategory(code, name, parentCategoryId)
}

