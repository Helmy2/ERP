package org.example.erp.features.inventory.domain.useCase.category

import org.example.erp.features.inventory.domain.repository.InventoryReps

class DeleteCategoryUseCase(
    private val repository: InventoryReps
) {
    suspend operator fun invoke(id: String) = repository.deleteCategory(id)
}