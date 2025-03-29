package org.example.erp.features.inventory.domain.mapper

import kotlinx.datetime.Clock
import org.example.erp.features.inventory.data.model.AuditLogsResponse
import org.example.erp.features.inventory.data.model.InventoryTransactionDetailsResponse
import org.example.erp.features.inventory.data.model.InventoryTransactionsResponse
import org.example.erp.features.inventory.data.model.PermissionsResponse
import org.example.erp.features.inventory.data.model.ProductCategoriesResponse
import org.example.erp.features.inventory.data.model.ProductsResponse
import org.example.erp.features.inventory.data.model.RolePermissionsResponse
import org.example.erp.features.inventory.data.model.StockLevelsResponse
import org.example.erp.features.inventory.data.model.UnitsOfMeasureResponse
import org.example.erp.features.inventory.data.model.UserRolesResponse
import org.example.erp.features.inventory.data.model.WarehouseResponse
import org.example.erp.features.inventory.domain.entity.AuditLogs
import org.example.erp.features.inventory.domain.entity.InventoryTransactionDetails
import org.example.erp.features.inventory.domain.entity.InventoryTransactions
import org.example.erp.features.inventory.domain.entity.Permissions
import org.example.erp.features.inventory.domain.entity.ProductCategories
import org.example.erp.features.inventory.domain.entity.Products
import org.example.erp.features.inventory.domain.entity.RolePermissions
import org.example.erp.features.inventory.domain.entity.StockLevels
import org.example.erp.features.inventory.domain.entity.UnitsOfMeasure
import org.example.erp.features.inventory.domain.entity.UserRoles
import org.example.erp.features.inventory.domain.entity.Warehouses


// Mapper extension functions

fun UnitsOfMeasureResponse.toDomain(): UnitsOfMeasure = UnitsOfMeasure(
    id = id,
    code = code,
    name = name,
    description = description,
    createdAt = createdAt!!,
    updatedAt = updatedAt,
    createdBy = createdBy!!,
    updatedBy = updatedBy
)

fun ProductCategoriesResponse.toDomain(): ProductCategories = ProductCategories(
    id = id!!,
    code = code,
    name = name,
    parentCategoryId = parentCategoryId,
    createdAt = createdAt!!,
    updatedAt = updatedAt,
    createdBy = createdBy!!,
    updatedBy = updatedBy
)

fun ProductsResponse.toDomain(): Products = Products(
    id = id!!,
    code = code,    name = name,
    sku = sku.orEmpty(),
    description = description.orEmpty(),
    categoryId = categoryId.orEmpty(),
    unitPrice = unitPrice,
    costPrice = costPrice ?: 0.0,
    minStockLevel = minStockLevel ?: 0,
    maxStockLevel = maxStockLevel,
    expiryDate = expiryDate,
    isActive = isActive ?: true,
    deletedAt = deletedAt,
    unitOfMeasureId = unitOfMeasureId,
    createdAt = createdAt!!,
    updatedAt = updatedAt,
    createdBy = createdBy!!,
    updatedBy = updatedBy
)

fun WarehouseResponse.toDomain(): Warehouses = Warehouses(
    id = id!!,
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