package org.example.erp.features.inventory.data.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PermissionsResponse(
    val id: String,
    val name: String,
    val description: String? = null,
    @SerialName("created_at")
    val createdAt: Instant? = null,
    @SerialName("updated_at")
    val updatedAt: Instant? = null
)