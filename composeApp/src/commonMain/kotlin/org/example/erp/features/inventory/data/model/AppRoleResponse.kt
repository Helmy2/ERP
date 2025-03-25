package org.example.erp.features.inventory.data.model

import kotlinx.serialization.Serializable

@Serializable
enum class AppRoleResponse {
    ADMIN,
    INVENTORY_MANAGER,
}