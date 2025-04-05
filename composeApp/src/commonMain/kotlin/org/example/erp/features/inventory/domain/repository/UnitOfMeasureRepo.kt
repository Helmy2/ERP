package org.example.erp.features.inventory.domain.repository

import org.example.erp.features.inventory.domain.entity.UnitOfMeasure

interface UnitOfMeasureRepo {
    fun syncUnitsOfMeasure(): Result<Unit>

    suspend fun getUnitOfMeasureByCode(code: String): Result<UnitOfMeasure>

    suspend fun getUnitOfMeasuresById(id: String): Result<UnitOfMeasure>

    suspend fun getAllUnitsOfMeasure(query: String): Result<List<UnitOfMeasure>>

    suspend fun createUnitOfMeasure(
        code: String, name: String, description: String
    ): Result<Unit>

    suspend fun updateUnitOfMeasure(
        id: String, code: String, name: String, description: String
    ): Result<Unit>

    suspend fun deleteUnitOfMeasure(code: String): Result<Unit>
}