package org.example.erp.features.inventory.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RolePermissionsResponse(
    val role: AppRoleResponse,
    @SerialName("permission_id")
    val permissionId: String
)