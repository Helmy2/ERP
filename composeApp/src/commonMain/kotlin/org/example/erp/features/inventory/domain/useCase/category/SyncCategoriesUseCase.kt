package org.example.erp.features.inventory.domain.useCase.category

import org.example.erp.features.inventory.domain.repository.InventoryReps

class SyncCategoriesUseCase(
    private val repository: InventoryReps
) {
    operator fun invoke() = repository.syncCategories()
}