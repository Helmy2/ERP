package org.example.erp.features.inventory.domain.useCase.category

import org.example.erp.features.inventory.domain.repository.InventoryReps

class GetCategoryByCodeUseCase(
    private val repository: InventoryReps
) {
    suspend operator fun invoke(code: String) = repository.getCategory(code)
}