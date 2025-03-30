package org.example.erp.features.inventory.presentation.inventory

import org.example.erp.features.inventory.domain.entity.InventoryDestination

data class InventoryState(
    val selectedDestination: InventoryDestination? = null,
)