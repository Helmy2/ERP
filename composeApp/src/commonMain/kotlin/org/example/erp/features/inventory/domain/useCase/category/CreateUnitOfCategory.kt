package org.example.erp.features.inventory.domain.useCase.category

import org.example.erp.features.inventory.domain.repository.InventoryReps

class CreateUnitOfCategory(
    private val repository: InventoryReps
) {
    suspend operator fun invoke(
        code: String,
        name: String,
        parentCategoryId: String?
    ) = repository.createCategory(code, name, parentCategoryId)
}

