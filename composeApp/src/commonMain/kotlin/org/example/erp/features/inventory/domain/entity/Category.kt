package org.example.erp.features.inventory.domain.entity

import kotlinx.datetime.Instant

data class Category(
    val id: String,
    val code: String,
    val name: String,
    val children: List<Category>,
    val parentCategory: Category?,
    val createdAt: Instant,
    val updatedAt: Instant? = null,
    val createdBy: String,
    val updatedBy: String? = null
)