package org.example.erp.features.inventory.data.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.erp.features.inventory.domain.entity.TransactionType

@Serializable
data class InventoryTransactionsResponse(
    val id: String? = null,
    val code: String,
    @SerialName("warehouse_id")
    val warehouseId: String,
    @SerialName("transaction_type")
    val transactionType: TransactionType,
    @SerialName("transaction_date")
    val transactionDate: Instant? = null,
    val notes: String? = null,
    @SerialName("created_at")
    val createdAt: Instant? = null,
    @SerialName("updated_at")
    val updatedAt: Instant? = null,
    @SerialName("created_by")
    val createdBy: String? = null,
    @SerialName("updated_by")
    val updatedBy: String? = null
)