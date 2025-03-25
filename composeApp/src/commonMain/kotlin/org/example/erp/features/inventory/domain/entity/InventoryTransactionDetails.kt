package org.example.erp.features.inventory.domain.entity

data class InventoryTransactionDetails(
    val id: String,
    val transactionId: String,
    val productId: String,
    val quantity: Int
)