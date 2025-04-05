package org.example.erp.features.inventory.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.example.erp.features.inventory.data.model.ProductResponse
import org.example.erp.features.inventory.data.model.WarehouseResponse

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: List<ProductResponse>)

    @Delete
    suspend fun delete(list: List<ProductResponse>)

    @Query("SELECT * FROM ProductResponse WHERE name LIKE '%' || :query || '%' OR code LIKE '%' || :query || '%'")
    suspend fun getAll(query: String): List<ProductResponse>

    @Query("SELECT * FROM ProductResponse WHERE code = :code")
    suspend fun getByCode(code: String): ProductResponse
}