package org.example.erp.features.inventory.domain.entity

enum class InventoryDestination {
    UnitOfMeasures, Warehouses, Categories;

    companion object {
        fun getAll(): List<InventoryDestination> = listOf(UnitOfMeasures, Warehouses, Categories)
    }
}