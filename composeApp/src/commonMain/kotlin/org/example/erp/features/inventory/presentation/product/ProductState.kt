package org.example.erp.features.inventory.presentation.product

import org.example.erp.features.inventory.domain.entity.Category
import org.example.erp.features.inventory.domain.entity.Product
import org.example.erp.features.inventory.domain.entity.UnitOfMeasure
import org.example.erp.features.user.domain.entity.User

data class ProductState(
    val loading: Boolean = false,
    val searchResults: List<Product> = emptyList(),
    val categoryList: List<Category> = emptyList(),
    val unitOfMeasureList: List<UnitOfMeasure> = emptyList(),
    val selectedProduct: Product? = null,
    val code: String = "",
    val name: String = "",
    val sku: String = "",
    val description: String = "",
    val unitPrice: String = "",
    val costPrice: String = "",
    val unitOfMeasureCode: String = "",
    val categoryCode: String = "",
    val isUnitOfMeasureDialogOpen: Boolean = false,
    val isCategoryDialogOpen: Boolean = false,
    val query: String = "",
    val isQueryActive: Boolean = false,
    val getUserById: suspend (String) -> Result<User>
) {
    val isNew: Boolean get() = selectedProduct == null
}