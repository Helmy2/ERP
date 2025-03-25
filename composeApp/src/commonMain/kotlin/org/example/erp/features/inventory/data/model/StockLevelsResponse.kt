package org.example.erp.features.inventory.data.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StockLevelsResponse(
    @SerialName("product_id")
    val productId: String,
    @SerialName("warehouse_id")
    val warehouseId: String,
    @SerialName("current_quantity")
    val currentQuantity: Int? = null,
    @SerialName("last_updated")
    val lastUpdated: Instant? = null
)