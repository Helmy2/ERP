package org.example.erp.features.inventory.domain.entity

import kotlinx.datetime.Instant

data class Product(
    override val id: String,
    override val code: String,
    val name: String,
    val sku: String,
    val description: String,
    val unitPrice: Double,
    val costPrice: Double,
    val categoryId: String?,
    val unitOfMeasureId: String?,
    val deletedAt: Instant? = null,
    override val createdAt: Instant,
    override val updatedAt: Instant? = null,
    override val createdBy: String,
    override val updatedBy: String? = null
) : Item