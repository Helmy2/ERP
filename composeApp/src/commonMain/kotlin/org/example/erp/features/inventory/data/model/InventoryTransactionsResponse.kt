package org.example.erp.features.inventory.data.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.erp.core.util.SupabaseConfig
import org.example.erp.features.inventory.domain.entity.TransactionType

@Serializable
data class InventoryTransactionsResponse(
    @SerialName(SupabaseConfig.ID_ROW)
    val id: String? = null,
    @SerialName(SupabaseConfig.CODE_ROW)
    val code: String,
    @SerialName(SupabaseConfig.NOTES_ROW)
    val notes: String? = null,
    @SerialName(SupabaseConfig.WAREHOUSE_ID_ROW)
    val warehouseId: String,
    @SerialName(SupabaseConfig.TRANSACTION_TYPE_ROW)
    val transactionType: TransactionType,
    @SerialName(SupabaseConfig.TRANSACTION_DATE_ROW)
    val transactionDate: Instant? = null,
    @SerialName(SupabaseConfig.CREATED_AT_ROW)
    val createdAt: Instant? = null,
    @SerialName(SupabaseConfig.UPDATED_AT_ROW)
    val updatedAt: Instant? = null,
    @SerialName(SupabaseConfig.CREATED_BY_ROW)
    val createdBy: String? = null,
    @SerialName(SupabaseConfig.UPDATED_BY_ROW)
    val updatedBy: String? = null
)