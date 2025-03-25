package org.example.erp.features.inventory.domain.entity

import kotlinx.datetime.Instant

data class ProductCategories(
    val id: String,
    val code: String,
    val name: String,
    val parentCategoryId: String? = null,
    val createdAt: Instant,
    val updatedAt: Instant? = null,
    val createdBy: String,
    val updatedBy: String? = null
)