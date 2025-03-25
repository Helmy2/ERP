package org.example.erp.features.inventory.domain.entity

import kotlinx.datetime.Instant

data class Warehouses(
    val id: String,
    val code: String,
    val name: String,
    val location: String,
    val capacity: Long,
    val createdAt: Instant,
    val updatedAt: Instant? = null,
    val createdBy: String,
    val updatedBy: String? = null
)