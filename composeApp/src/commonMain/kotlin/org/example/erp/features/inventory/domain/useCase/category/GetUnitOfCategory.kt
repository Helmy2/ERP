package org.example.erp.features.inventory.domain.useCase.category

import org.example.erp.features.inventory.domain.repository.InventoryReps

class GetUnitOfCategory(
    private val repository: InventoryReps
) {
    operator fun invoke() = repository.getCategories()
}