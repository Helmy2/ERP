package org.example.erp.features.inventory.domain.entity

data class InventoryTransactionDetails(
    val id: String,
    val product: Product,
    val quantity: Int
)