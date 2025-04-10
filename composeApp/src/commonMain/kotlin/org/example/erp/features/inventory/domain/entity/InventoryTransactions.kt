package org.example.erp.features.inventory.domain.entity

import kotlinx.datetime.Instant

data class InventoryTransactions(
    override val id: String,
    override val code: String,
    val warehouseId: String,
    val transactionType: TransactionType,
    val notes: String,
    val listOfItem: List<InventoryTransactionDetails>,
    val transactionDate: Instant,
    override val createdAt: Instant,
    override val updatedAt: Instant? = null,
    override val createdBy: String,
    override val updatedBy: String? = null
) : Item