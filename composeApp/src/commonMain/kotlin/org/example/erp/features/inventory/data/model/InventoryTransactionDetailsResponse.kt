package org.example.erp.features.inventory.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.erp.core.util.SupabaseConfig

@Serializable
data class InventoryTransactionDetailsResponse(
    @SerialName(SupabaseConfig.ID_ROW)
    val id: String,
    @SerialName(SupabaseConfig.TRANSACTION_ID_ROW)
    val transactionId: String,
    @SerialName(SupabaseConfig.PRODUCT_ID_ROW)
    val productId: String,
    @SerialName(SupabaseConfig.QUANTITY_ROW)
    val quantity: Int
)