package org.example.erp.features.inventory.domain.useCase.category

import org.example.erp.features.inventory.domain.repository.CategoryRepo

class SyncCategoriesUseCase(
    private val repository: CategoryRepo
) {
    operator fun invoke() = repository.syncCategories()
}