package org.example.erp.features.inventory.domain.entity

import kotlinx.datetime.Instant

data class Warehouses(
    override val id: String,
    override val code: String,
    val name: String,
    val location: String,
    val capacity: Long,
    override val createdAt: Instant,
    override val updatedAt: Instant? = null,
    override val createdBy: String,
    override val updatedBy: String? = null
) : Item