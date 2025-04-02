package org.example.erp.features.inventory.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.example.erp.features.inventory.data.model.UnitsOfMeasureResponse

@Dao
interface UnitsOfMeasureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: List<UnitsOfMeasureResponse>)

    @Delete
    suspend fun delete(list: List<UnitsOfMeasureResponse>)

    @Query("SELECT * FROM UnitsOfMeasureResponse WHERE name LIKE '%' || :query || '%' OR code LIKE '%' || :query || '%'")
    suspend fun getAll(query: String): List<UnitsOfMeasureResponse>

    @Query("SELECT * FROM UnitsOfMeasureResponse WHERE code = :code")
    suspend fun getByCode(code: String): UnitsOfMeasureResponse
}