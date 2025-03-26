package org.example.erp.features.inventory.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.erp.features.inventory.domain.entity.UnitsOfMeasure


interface InventoryReps {
    fun getAllUnitsOfMeasure(): Flow<List<UnitsOfMeasure>>
    suspend fun getUnitOfMeasureByCode(code: String): Result<UnitsOfMeasure>
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
}