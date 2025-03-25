package org.example.erp.features.inventory.domain.entity

import kotlinx.datetime.Instant

data class AuditLogs(
    val id: String,
    val userId: String? = null,
    val actionType: String,
    val tableName: String,
    val recordId: String? = null,
    val oldValues: String? = null,
    val newValues: String? = null,
    val executedAt: Instant
)