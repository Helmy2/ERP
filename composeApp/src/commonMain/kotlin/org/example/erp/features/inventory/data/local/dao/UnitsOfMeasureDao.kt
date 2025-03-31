package org.example.erp.features.inventory.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.example.erp.features.inventory.data.model.UnitsOfMeasureResponse

@Dao
interface UnitsOfMeasureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: List<UnitsOfMeasureResponse>)

    @Query("DELETE FROM UnitsOfMeasureResponse")
    suspend fun clear()

    @Delete
    suspend fun delete(list: List<UnitsOfMeasureResponse>)

    @Query("SELECT * FROM UnitsOfMeasureResponse")
    fun getAll(): Flow<List<UnitsOfMeasureResponse>>
}