package org.example.erp.features.inventory.presentation.product


sealed interface ProductEvent {
    data object CreateProduct : ProductEvent
    data object UpdateProduct : ProductEvent
    data object DeleteProduct : ProductEvent
    data class SearchProduct(val query: String) : ProductEvent
    data class UpdateCode(val code: String) : ProductEvent
    data class UpdateName(val name: String) : ProductEvent
    data class UpdateSku(val sku: String) : ProductEvent
    data class UpdateDescription(val description: String) : ProductEvent
    data class UpdateUnitPrice(val unitPrice: Double) : ProductEvent
    data class UpdateCostPrice(val costPrice: Double) : ProductEvent
    data class UpdateUnitOfMeasureCode(val code: String) : ProductEvent
    data class UpdateCategoryCode(val code: String) : ProductEvent
    data class UpdateIsUnitOfMeasureDialogOpen(val open: Boolean) : ProductEvent
    data class UpdateIsCategoryDialogOpen(val open: Boolean) : ProductEvent
    data class UpdateQuery(val query: String) : ProductEvent
    data class UpdateIsQueryActive(val isQueryActive: Boolean) : ProductEvent
    data class Search(val query: String) : ProductEvent
}