package org.example.erp.features.inventory.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InventoryTransactionDetailsResponse(
    val id: String,
    @SerialName("transaction_id")
    val transactionId: String,
    @SerialName("product_id")
    val productId: String,
    val quantity: Int
)