package org.example.erp.features.inventory.domain.mapper

import kotlinx.datetime.Clock
import org.example.erp.features.inventory.data.model.AuditLogsResponse
import org.example.erp.features.inventory.data.model.CategoryResponse
import org.example.erp.features.inventory.data.model.InventoryTransactionDetailsResponse
import org.example.erp.features.inventory.data.model.InventoryTransactionsResponse
import org.example.erp.features.inventory.data.model.PermissionsResponse
import org.example.erp.features.inventory.data.model.ProductResponse
import org.example.erp.features.inventory.data.model.RolePermissionsResponse
import org.example.erp.features.inventory.data.model.StockLevelsResponse
import org.example.erp.features.inventory.data.model.UnitsOfMeasureResponse
import org.example.erp.features.inventory.data.model.UserRolesResponse
import org.example.erp.features.inventory.data.model.WarehouseResponse
import org.example.erp.features.inventory.domain.entity.AuditLogs
import org.example.erp.features.inventory.domain.entity.Category
import org.example.erp.features.inventory.domain.entity.InventoryTransactionDetails
import org.example.erp.features.inventory.domain.entity.InventoryTransactions
import org.example.erp.features.inventory.domain.entity.Permissions
import org.example.erp.features.inventory.domain.entity.Product
import org.example.erp.features.inventory.domain.entity.RolePermissions
import org.example.erp.features.inventory.domain.entity.StockLevels
import org.example.erp.features.inventory.domain.entity.UnitOfMeasure
import org.example.erp.features.inventory.domain.entity.UserRoles
import org.example.erp.features.inventory.domain.entity.Warehouses


// Mapper extension functions

fun UnitsOfMeasureResponse.toDomain(): UnitOfMeasure = UnitOfMeasure(
    id = id,
    code = code,
    name = name,
    description = description,
    createdAt = createdAt!!,
    updatedAt = updatedAt,
    createdBy = createdBy!!,
    updatedBy = updatedBy
)

fun CategoryResponse.toDomain(children: List<Category>, parentCategory: Category?): Category =
    Category(
        id = id,
        code = code,
        name = name,
        children = children,
        parentCategory = parentCategory,
        createdAt = createdAt!!,
        updatedAt = updatedAt,
        createdBy = createdBy!!,
        updatedBy = updatedBy
    )

fun ProductResponse.toDomain(): Product = Product(
    id = id,
    code = code, name = name,
    sku = sku.orEmpty(),
    description = description.orEmpty(),
    categoryId = categoryId,
    unitPrice = unitPrice ?: 0.0,
    costPrice = costPrice ?: 0.0,
    deletedAt = deletedAt,
    unitOfMeasureId = unitOfMeasureId,
    createdAt = createdAt!!,
    updatedAt = updatedAt,
    createdBy = createdBy!!,
    updatedBy = updatedBy
)

fun WarehouseResponse.toDomain(): Warehouses = Warehouses(
    id = id,
    code = code,
    name = name,
    location = location.orEmpty(),
    capacity = capacity ?: 0,
    createdAt = createdAt!!,
    updatedAt = updatedAt,
    createdBy = createdBy!!,
    updatedBy = updatedBy
)

fun InventoryTransactionsResponse.toDomain(list: List<InventoryTransactionDetails> = emptyList()): InventoryTransactions =
    InventoryTransactions(
        id = id!!,
        code = code,
        warehouseId = warehouseId,
        transactionType = transactionType,
        transactionDate = transactionDate ?: Clock.System.now(),
        notes = notes ?: "",
        createdAt = createdAt!!,
        updatedAt = updatedAt,
        createdBy = createdBy!!,
        updatedBy = updatedBy,
        listOfItem = list
    )

fun InventoryTransactionDetailsResponse.toDomain(): InventoryTransactionDetails =
    InventoryTransactionDetails(
        id = id,
        transactionId = transactionId,
        productId = productId,
        quantity = quantity
    )

fun StockLevelsResponse.toDomain(): StockLevels = StockLevels(
    productId = productId,
    warehouseId = warehouseId,
    currentQuantity = currentQuantity ?: 0,
    lastUpdated = lastUpdated ?: Clock.System.now()
)

fun PermissionsResponse.toDomain(): Permissions = Permissions(
    id = id,
    name = name,
    description = description,
    createdAt = createdAt!!,
    updatedAt = updatedAt
)

fun RolePermissionsResponse.toDomain(): RolePermissions = RolePermissions(
    role = role,
    permissionId = permissionId
)

fun UserRolesResponse.toDomain(): UserRoles = UserRoles(
    userId = userId,
    role = role,
    assignedAt = assignedAt ?: Clock.System.now(),
    assignedBy = assignedBy
)

fun AuditLogsResponse.toDomain(): AuditLogs = AuditLogs(
    id = id,
    userId = userId,
    actionType = actionType,
    tableName = tableName,
    recordId = recordId,
    oldValues = oldValues,
    newValues = newValues,
    executedAt = executedAt
)