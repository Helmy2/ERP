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

    @Delete
    suspend fun delete(list: List<CategoryResponse>)

    @Query("SELECT * FROM CategoryResponse")
    fun getAll(): Flow<List<CategoryResponse>>

    @Query("SELECT * FROM CategoryResponse WHERE code = :code")
    suspend fun getByCode(code: String): CategoryResponse

    @Query("SELECT * FROM CategoryResponse WHERE id = :id")
    suspend fun getById(id: String): CategoryResponse

    @Query("SELECT * FROM CategoryResponse WHERE parentCategoryId = :id")
    suspend fun getChildren(id: String): List<CategoryResponse>

}