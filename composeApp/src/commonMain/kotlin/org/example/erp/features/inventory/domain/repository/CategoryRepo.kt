package org.example.erp.features.inventory.domain.repository

import org.example.erp.features.inventory.domain.entity.Category

interface CategoryRepo {

    suspend fun getCategoryByCode(code: String): Result<Category>

    suspend fun getCategoryById(id: String): Result<Category>

    fun syncCategories(): Result<Unit>

    suspend fun getCategories(query: String): Result<List<Category>>

    suspend fun createCategory(
        code: String, name: String, parentCategoryId: String?
    ): Result<Unit>

    suspend fun updateCategory(
        id: String, code: String, name: String, parentCategoryId: String?
    ): Result<Unit>

    suspend fun deleteCategoryByCode(code: String): Result<Unit>
}