package org.example.erp.features.inventory.presentation.warehouses

sealed interface WarehouseEvent {
    data object CreateWarehouse : WarehouseEvent
    data object UpdateWarehouse : WarehouseEvent
    data object DeleteWarehouse : WarehouseEvent

    data class SearchWarehouse(val code: String) : WarehouseEvent
    data class UpdateCode(val code: String) : WarehouseEvent
    data class UpdateName(val name: String) : WarehouseEvent
    data class UpdateCapacity(val capacity: Long) : WarehouseEvent
    data class UpdateLocation(val location: String) : WarehouseEvent
    data class UpdateQuery(val query: String) : WarehouseEvent
    data class UpdateIsQueryActive(val isQueryActive: Boolean) : WarehouseEvent
    data class Search(val query: String) : WarehouseEvent
}