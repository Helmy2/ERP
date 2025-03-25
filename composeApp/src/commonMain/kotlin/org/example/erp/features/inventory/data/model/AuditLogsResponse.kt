package org.example.erp.features.inventory.data.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuditLogsResponse(
    val id: String,
    @SerialName("user_id")
    val userId: String? = null,
    @SerialName("action_type")
    val actionType: String,
    @SerialName("table_name")
    val tableName: String,
    @SerialName("record_id")
    val recordId: String? = null,
    @SerialName("old_values")
    val oldValues: String? = null,
    @SerialName("new_values")
    val newValues: String? = null,
    @SerialName("executed_at")
    val executedAt: Instant
)