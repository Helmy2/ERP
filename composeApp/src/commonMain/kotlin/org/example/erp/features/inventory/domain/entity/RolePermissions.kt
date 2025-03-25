package org.example.erp.features.inventory.domain.entity

data class RolePermissions(
    val role: AppRole,
    val permissionId: String
)