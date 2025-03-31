package org.example.erp.features.inventory.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.example.erp.features.inventory.data.model.CategoryResponse

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: List<CategoryResponse>)

    @Query("DELETE FROM CategoryResponse")
    suspend fun clear()

    @Delete
    suspend fun delete(list: List<CategoryResponse>)

    @Query("SELECT * FROM CategoryResponse")
    fun getAll(): Flow<List<CategoryResponse>>
}