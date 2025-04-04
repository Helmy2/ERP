package org.example.erp.features.inventory.domain.repository

import org.example.erp.features.inventory.domain.entity.UnitsOfMeasure

interface UnitsOfMeasureRepo {
    fun syncUnitsOfMeasure(): Result<Unit>

    suspend fun getUnitOfMeasure(code: String): Result<UnitsOfMeasure>

    suspend fun getAllUnitsOfMeasure(query: String): Result<List<UnitsOfMeasure>>

    suspend fun createUnitOfMeasure(
        code: String, name: String, description: String
    ): Result<Unit>

    suspend fun updateUnitOfMeasure(
        id: String, code: String, name: String, description: String
    ): Result<Unit>

    suspend fun deleteUnitOfMeasure(code: String): Result<Unit>
}