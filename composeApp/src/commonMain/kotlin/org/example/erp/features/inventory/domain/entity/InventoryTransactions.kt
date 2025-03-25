package org.example.erp.features.inventory.domain.entity

import kotlinx.datetime.Instant

data class InventoryTransactions(
    val id: String,
    val code: String,
    val warehouseId: String,
    val transactionType: TransactionType,
    val transactionDate: Instant,
    val notes: String,
    val createdAt: Instant,
    val listOfItem: List<InventoryTransactionDetails>,
    val updatedAt: Instant? = null,
    val createdBy: String,
    val updatedBy: String? = null,
)