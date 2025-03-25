package org.example.erp.features.inventory.domain.entity

import kotlinx.datetime.Instant

data class UnitsOfMeasure(
    val id: String,
    val name: String,
    val code: String,
    val description: String? = null,
    val createdAt: Instant,
    val updatedAt: Instant? = null,
    val createdBy: String,
    val updatedBy: String? = null
)