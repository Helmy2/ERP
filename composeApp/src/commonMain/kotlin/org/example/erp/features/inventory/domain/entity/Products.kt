package org.example.erp.features.inventory.domain.entity

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

data class Products(
    val id: String,
    val code: String,
    val name: String,
    val sku: String,
    val description: String,
    val categoryId: String,
    val unitPrice: Double,
    val costPrice: Double,
    val minStockLevel: Int,
    val maxStockLevel: Int? = null,
    val expiryDate: LocalDate? = null,
    val isActive: Boolean,
    val deletedAt: Instant? = null,
    val unitOfMeasureId: String,
    val createdAt: Instant,
    val updatedAt: Instant? = null,
    val createdBy: String,
    val updatedBy: String? = null
)