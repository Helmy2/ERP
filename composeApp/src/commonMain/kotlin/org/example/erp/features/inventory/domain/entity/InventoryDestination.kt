package org.example.erp.features.inventory.domain.entity

sealed interface InventoryDestination {
    data object UnitOfMeasures : InventoryDestination
    data object Warehouses : InventoryDestination

    companion object {
        fun getAll(): List<InventoryDestination> = listOf(UnitOfMeasures, Warehouses)
    }
}