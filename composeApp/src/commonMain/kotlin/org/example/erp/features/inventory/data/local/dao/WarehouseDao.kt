package org.example.erp.features.inventory.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.example.erp.features.inventory.data.model.UnitsOfMeasureResponse
import org.example.erp.features.inventory.data.model.WarehouseResponse

@Dao
interface WarehouseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: List<WarehouseResponse>)

    @Query("DELETE FROM WarehouseResponse")
    suspend fun clear()

    @Delete
    suspend fun delete(list: List<WarehouseResponse>)

    @Query("SELECT * FROM WarehouseResponse")
    fun getAll(): Flow<List<WarehouseResponse>>
}