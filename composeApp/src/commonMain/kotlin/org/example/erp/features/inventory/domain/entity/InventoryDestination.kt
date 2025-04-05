package org.example.erp.features.inventory.domain.entity

enum class InventoryDestination {
    UnitOfMeasures, Warehouses, Categories, Products;

    companion object {
        fun getAll(): List<InventoryDestination> = listOf(UnitOfMeasures, Warehouses, Categories, Products)
    }
}