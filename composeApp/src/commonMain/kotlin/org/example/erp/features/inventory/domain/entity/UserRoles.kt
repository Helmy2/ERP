package org.example.erp.features.inventory.domain.entity

import kotlinx.datetime.Instant

data class UserRoles(
    val userId: String,
    val role: AppRole,
    val assignedAt: Instant,
    val assignedBy: String? = null
)