package org.example.erp.features.inventory.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.erp.features.inventory.domain.entity.Category
import org.example.erp.features.inventory.domain.entity.UnitsOfMeasure
import org.example.erp.features.inventory.domain.entity.Warehouses


interface InventoryReps {
    fun getAllUnitsOfMeasure(): Flow<Result<List<UnitsOfMeasure>>>

    suspend fun createUnitOfMeasure(
        code: String, name: String, description: String
    ): Result<Unit>

    suspend fun updateUnitOfMeasure(
        id: String, code: String, name: String, description: String
    ): Result<Unit>

    suspend fun deleteUnitOfMeasure(code: String): Result<Unit>

    fun getAllWarehouse(): Flow<Result<List<Warehouses>>>

    suspend fun createWarehouse(
        code: String, name: String, capacity: Long?, location: String
    ): Result<Unit>

    suspend fun updateWarehouse(
        id: String, code: String, name: String, capacity: Long?, location: String
    ): Result<Unit>

    suspend fun deleteWarehouse(code: String): Result<Unit>

    fun getCategories(): Flow<Result<List<Category>>>

    suspend fun createCategory(
        code: String, name: String, parentCategoryId: String?
    ): Result<Unit>

    suspend fun updateCategory(
        id: String, code: String, name: String, parentCategoryId: String?
    ): Result<Unit>

    suspend fun deleteCategory(code: String): Result<Unit>
}