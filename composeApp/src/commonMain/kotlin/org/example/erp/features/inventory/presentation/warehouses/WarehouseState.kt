package org.example.erp.features.inventory.presentation.warehouses

import org.example.erp.features.inventory.domain.entity.Warehouses

data class WarehouseState(
    val loading: Boolean = true,
    val warehousesList: List<Warehouses> = emptyList(),
    val selectedWarehouse: Warehouses? = null,
    val code: String = "",
    val name: String = "",
    val capacity: Long? = null,
    val location: String = "",
    val getDisplayNameForUser: suspend (String) -> String
) {
    val isNew: Boolean get() = selectedWarehouse == null
}