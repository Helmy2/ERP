package org.example.erp.features.inventory.presentation.warehouses

import org.example.erp.features.inventory.domain.entity.Warehouses
import org.example.erp.features.user.domain.entity.User

data class WarehouseState(
    val loading: Boolean = false,
    val searchResults: List<Warehouses> = emptyList(),
    val selectedWarehouse: Warehouses? = null,
    val code: String = "",
    val name: String = "",
    val capacity: Long? = null,
    val location: String = "",
    val query: String = "",
    val isQueryActive: Boolean = false,
    val getUserById: suspend (String) -> Result<User>
) {
    val isNew: Boolean get() = selectedWarehouse == null
}