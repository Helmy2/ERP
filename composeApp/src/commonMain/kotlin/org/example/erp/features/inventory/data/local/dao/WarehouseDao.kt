package org.example.erp.features.inventory.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.example.erp.features.inventory.data.model.WarehouseResponse

@Dao
interface WarehouseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: List<WarehouseResponse>)

    @Delete
    suspend fun delete(list: List<WarehouseResponse>)

    @Query("SELECT * FROM WarehouseResponse WHERE name LIKE '%' || :query || '%' OR code LIKE '%' || :query || '%'")
    suspend fun getAll(query: String): List<WarehouseResponse>

    @Query("SELECT * FROM WarehouseResponse WHERE code = :code")
    suspend fun getByCode(code: String): WarehouseResponse
}