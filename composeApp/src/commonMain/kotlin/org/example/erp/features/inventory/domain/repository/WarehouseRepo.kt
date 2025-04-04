package org.example.erp.features.inventory.domain.repository

import org.example.erp.features.inventory.domain.entity.Warehouses

interface WarehouseRepo {
    fun syncWarehouse(): Result<Unit>

    suspend fun getWarehouse(code: String): Result<Warehouses>

    suspend fun getAllWarehouse(query: String): Result<List<Warehouses>>

    suspend fun createWarehouse(
        code: String, name: String, capacity: Long?, location: String
    ): Result<Unit>

    suspend fun updateWarehouse(
        id: String, code: String, name: String, capacity: Long?, location: String
    ): Result<Unit>

    suspend fun deleteWarehouse(code: String): Result<Unit>
}