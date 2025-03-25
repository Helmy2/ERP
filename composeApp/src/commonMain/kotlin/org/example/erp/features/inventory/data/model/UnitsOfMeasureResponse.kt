package org.example.erp.features.inventory.data.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnitsOfMeasureResponse(
    val id: String,
    val name: String,
    val code: String,
    val description: String? = null,
    @SerialName("created_at")
    val createdAt: Instant,
    @SerialName("updated_at")
    val updatedAt: Instant? = null,
    @SerialName("created_by")
    val createdBy: String,
    @SerialName("updated_by")
    val updatedBy: String? = null
)