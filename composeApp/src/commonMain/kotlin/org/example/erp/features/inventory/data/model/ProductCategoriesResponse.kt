package org.example.erp.features.inventory.data.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductCategoriesResponse(
    val id: String? = null,
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