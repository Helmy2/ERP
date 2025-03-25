package org.example.erp.features.inventory.domain.entity

import kotlinx.datetime.Instant

data class StockLevels(
    val productId: String,
    val warehouseId: String,
    val currentQuantity: Int,
    val lastUpdated: Instant
)