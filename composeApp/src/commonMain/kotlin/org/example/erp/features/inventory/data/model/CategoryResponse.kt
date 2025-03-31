package org.example.erp.features.inventory.data.model

import androidx.room.Entity
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(primaryKeys = ["id"])
@Serializable
data class CategoryResponse(
    val id: String,
    val code: String,
    val name: String,
    @SerialName("parent_category_id")
    val parentCategoryId: String? = null,
    @SerialName("created_at")
    val createdAt: Instant? = null,
    @SerialName("updated_at")
    val updatedAt: Instant? = null,
    @SerialName("created_by")
    val createdBy: String? = null,
    @SerialName("updated_by")
    val updatedBy: String? = null
)