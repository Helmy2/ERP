package org.example.erp.features.inventory.domain.entity

import kotlinx.datetime.Instant

data class Permissions(
    val id: String,
    val name: String,
    val description: String? = null,
    val createdAt: Instant,
    val updatedAt: Instant? = null,
)