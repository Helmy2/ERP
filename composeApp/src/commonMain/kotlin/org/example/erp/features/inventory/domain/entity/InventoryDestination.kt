package org.example.erp.features.inventory.domain.entity

sealed interface InventoryDestination{
   data object UnitOfMeasures: InventoryDestination
   data object Warehouses: InventoryDestination

}