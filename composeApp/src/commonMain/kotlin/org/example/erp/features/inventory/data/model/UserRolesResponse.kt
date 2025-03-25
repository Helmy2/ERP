package org.example.erp.features.inventory.data.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.erp.features.inventory.domain.entity.AppRole

@Serializable
data class UserRolesResponse(
    @SerialName("user_id")
    val userId: String,
    val role: AppRole,
    @SerialName("assigned_at")
    val assignedAt: Instant? = null,
    @SerialName("assigned_by")
    val assignedBy: String? = null
)