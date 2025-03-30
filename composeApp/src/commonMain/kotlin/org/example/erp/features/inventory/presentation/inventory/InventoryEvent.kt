package org.example.erp.features.inventory.presentation.inventory

import org.example.erp.features.inventory.domain.entity.InventoryDestination

sealed interface InventoryEvent {
    data class UpdateSelectDestination(val destination: InventoryDestination) : InventoryEvent
}