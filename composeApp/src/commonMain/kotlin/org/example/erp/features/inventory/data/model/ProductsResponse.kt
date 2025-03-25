package org.example.erp.features.inventory.data.model

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductsResponse(
    val id: String,
    val code: String,
    val name: String,
    val sku: String,
    val description: String? = null,
    @SerialName("category_id")
    val categoryId: String? = null,
    @SerialName("unit_price")
    val unitPrice: Double,
    @SerialName("cost_price")
    val costPrice: Double? = null,
    @SerialName("min_stock_level")
    val minStockLevel: Int? = null,
    @SerialName("max_stock_level")
    val maxStockLevel: Int? = null,
    @SerialName("expiry_date")
    val expiryDate: LocalDate? = null,
    @SerialName("is_active")
    val isActive: Boolean? = null,
    @SerialName("deleted_at")
    val deletedAt: Instant? = null,
    @SerialName("unit_of_measure_id")
    val unitOfMeasureId: String,
    @SerialName("created_at")
    val createdAt: Instant,
    @SerialName("updated_at")
    val updatedAt: Instant? = null,
    @SerialName("created_by")
    val createdBy: String,
    @SerialName("updated_by")
    val updatedBy: String? = null
)