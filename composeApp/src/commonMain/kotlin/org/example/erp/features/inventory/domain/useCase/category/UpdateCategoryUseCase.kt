package org.example.erp.features.inventory.domain.useCase.category

import org.example.erp.features.inventory.domain.repository.InventoryReps

class UpdateCategoryUseCase(
    private val repository: InventoryReps
) {
    suspend operator fun invoke(
        id: String,
        code: String,
        name: String,
        parentCategoryId: String?
    ) = repository.updateCategory(id, code, name, parentCategoryId)
}