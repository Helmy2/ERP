package org.example.erp.features.inventory.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.erp.features.inventory.domain.entity.UnitsOfMeasure
import org.example.erp.features.inventory.domain.entity.Warehouses


interface InventoryReps {
    fun getAllUnitsOfMeasure(): Flow<List<UnitsOfMeasure>>
    suspend fun createUnitOfMeasure(
        code: String,
        name: String,
        description: String
    ): Result<Unit>

    suspend fun updateUnitOfMeasure(
        id: String,
        code: String,
        name: String,
        description: String
    ): Result<Unit>

    suspend fun deleteUnitOfMeasure(code: String): Result<Unit>
    @OptIn(SupabaseExperimental::class)
    fun getAllWarehouse(): Flow<List<Warehouses>>
    suspend fun createWarehouse(
        code: String,
        name: String,
        capacity: Long?,
        location: String?
    ): Result<Unit>

    suspend fun updateWarehouse(
        id: String,
        code: String,
        name: String,
        capacity: Long?,
        location: String?
    ): Result<Unit>

    suspend fun deleteWarehouse(code: String): Result<Unit>
}