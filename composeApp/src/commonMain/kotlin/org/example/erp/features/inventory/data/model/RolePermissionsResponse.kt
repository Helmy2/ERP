package org.example.erp.features.inventory.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.erp.features.inventory.domain.entity.AppRole

@Serializable
data class RolePermissionsResponse(
    val role: AppRole,
    @SerialName("permission_id")
    val permissionId: String
)