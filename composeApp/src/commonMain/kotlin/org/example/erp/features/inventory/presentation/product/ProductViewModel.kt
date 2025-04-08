package org.example.erp.features.inventory.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.error_creating_product
import erp.composeapp.generated.resources.error_deleting_product
import erp.composeapp.generated.resources.error_syncing_categories
import erp.composeapp.generated.resources.error_updating_product
import erp.composeapp.generated.resources.product_created
import erp.composeapp.generated.resources.product_deleted
import erp.composeapp.generated.resources.product_updated
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.erp.core.domain.snackbar.SnackbarManager
import org.example.erp.features.inventory.domain.useCase.category.GetAllCategoryUseCase
import org.example.erp.features.inventory.domain.useCase.category.GetCategoryByIdUseCase
import org.example.erp.features.inventory.domain.useCase.product.CreateProductUseCase
import org.example.erp.features.inventory.domain.useCase.product.DeleteProductUseCase
import org.example.erp.features.inventory.domain.useCase.product.GetAllProductUseCase
import org.example.erp.features.inventory.domain.useCase.product.GetProductByCodeUseCase
import org.example.erp.features.inventory.domain.useCase.product.SyncProductsUseCase
import org.example.erp.features.inventory.domain.useCase.product.UpdateProductUseCase
import org.example.erp.features.inventory.domain.useCase.unitOfMeasures.GetAllUnitsOfMeasureUseCase
import org.example.erp.features.inventory.domain.useCase.unitOfMeasures.GetUnitOfMeasuresByIdUseCase
import org.example.erp.features.user.domain.usecase.GetDisplayNameUseCase
import org.jetbrains.compose.resources.getString

class ProductViewModel(
    private val snackbarManager: SnackbarManager,
    private val getDisplayName: GetDisplayNameUseCase,
    private val getProduct: GetProductByCodeUseCase,
    private val createProduct: CreateProductUseCase,
    private val updateProduct: UpdateProductUseCase,
    private val deleteProduct: DeleteProductUseCase,
    private val getAllProduct: GetAllProductUseCase,
    private val syncProducts: SyncProductsUseCase,
    private val getCategoryById: GetCategoryByIdUseCase,
    private val getUnitOfMeasureById: GetUnitOfMeasuresByIdUseCase,
    private val getAllCategory: GetAllCategoryUseCase,
    private val getAllUnitsOfMeasure: GetAllUnitsOfMeasureUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProductState(getUserById = { getDisplayName(it) }))
    val state = _state.onStart {
        lead()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = ProductState(getUserById = { getDisplayName(it) })
    )

    private suspend fun lead() {
        viewModelScope.launch {
            launch {
                syncProducts().onFailure {
                    snackbarManager.showErrorSnackbar(
                        getString(Res.string.error_syncing_categories), it
                    )
                }
            }
            launch {
                search("")
            }
            launch {
                getAllCategory("").fold(
                    onSuccess = { categories -> _state.update { it.copy(categoryList = categories) } },
                    onFailure = {
                        snackbarManager.showErrorSnackbar(
                            getString(Res.string.error_syncing_categories), it
                        )
                    }
                )
            }
            launch {
                getAllUnitsOfMeasure("").fold(
                    onSuccess = { unitsOfMeasure ->
                        _state.update { it.copy(unitOfMeasureList = unitsOfMeasure) }
                    },
                    onFailure = {
                        snackbarManager.showErrorSnackbar(
                            getString(Res.string.error_syncing_categories), it
                        )
                    }
                )
            }
        }
    }

    fun handleEvent(event: ProductEvent) {
        when (event) {
            is ProductEvent.CreateProduct -> createProduct()
            is ProductEvent.UpdateProduct -> updateProduct()
            is ProductEvent.DeleteProduct -> deleteProduct()
            is ProductEvent.Search -> search(event.query)
            is ProductEvent.UpdateQuery -> updateQuery(event.query)
            is ProductEvent.UpdateIsQueryActive -> updateIsQueryActive(event.isQueryActive)
            is ProductEvent.UpdateCode -> updateCode(event.code)
            is ProductEvent.UpdateName -> updateName(event.name)
            is ProductEvent.UpdateDescription -> updateDescription(event.description)
            is ProductEvent.SearchProduct -> searchProduct(event.query)
            is ProductEvent.UpdateCategoryCode -> updateCategoryCode(event.code)
            is ProductEvent.UpdateCostPrice -> updateCostPrice(event.costPrice)
            is ProductEvent.UpdateIsCategoryDialogOpen -> updateIsCategoryDialogOpen(event.open)
            is ProductEvent.UpdateIsUnitOfMeasureDialogOpen -> updateIsUnitOfMeasureDialogOpen(event.open)
            is ProductEvent.UpdateSku -> updateSku(event.sku)
            is ProductEvent.UpdateUnitOfMeasureCode -> updateUnitOfMeasureCode(event.code)
            is ProductEvent.UpdateUnitPrice -> updateUnitPrice(event.unitPrice)
        }
    }

    private fun searchProduct(code: String) {
        viewModelScope.launch {
            _state.update { it.copy(code = code, loading = true) }

            getProduct(code).fold(onFailure = {
                _state.update {
                    it.copy(
                        name = "",
                        selectedProduct = null,
                        loading = false,
                        isUnitOfMeasureDialogOpen = false,
                        isCategoryDialogOpen = false,
                        unitOfMeasureCode = "",
                        categoryCode = "",
                        costPrice = null,
                        unitPrice = null,
                        sku = "",
                        description = "",
                    )
                }
            }, onSuccess = { product ->
                _state.update {
                    it.copy(
                        loading = false,
                        name = product.name,
                        selectedProduct = product,
                        sku = product.sku,
                        description = product.description,
                        unitPrice = product.unitPrice,
                        costPrice = product.costPrice,
                        unitOfMeasureCode = getUnitOfMeasureById(product.unitOfMeasureId ?: "").getOrNull()?.code ?: "",
                        categoryCode = getCategoryById(product.categoryId ?: "").getOrNull()?.code ?: "",
                        isUnitOfMeasureDialogOpen = false,
                        isCategoryDialogOpen = false,
                    )
                }
            })
        }
    }

    private fun updateIsUnitOfMeasureDialogOpen(open: Boolean) {
        _state.update { it.copy(isUnitOfMeasureDialogOpen = open) }
    }

    private fun updateIsCategoryDialogOpen(open: Boolean) {
        _state.update { it.copy(isCategoryDialogOpen = open) }
    }

    private fun search(query: String) {
        viewModelScope.launch {
            getAllProduct(query).fold(onSuccess = { list ->
                _state.update {
                    it.copy(
                        searchResults = list, loading = false
                    )
                }
            }, onFailure = { throwable ->
                _state.update { it.copy(loading = false) }
                println("Exception in ProductViewModel search: $throwable")
            })
        }
    }

    private fun updateQuery(query: String) {
        _state.update { it.copy(query = query) }
        search(query)
    }

    private fun updateIsQueryActive(queryActive: Boolean) {
        _state.update { it.copy(isQueryActive = queryActive) }
    }

    private fun updateCode(code: String) {
        _state.update { it.copy(code = code) }
    }

    private fun updateName(name: String) {
        _state.update { it.copy(name = name) }
    }

    private fun updateDescription(description: String) {
        _state.update { it.copy(description = description) }
    }

    private fun updateUnitPrice(unitPrice: Double) {
        _state.update { it.copy(unitPrice = unitPrice) }
    }

    private fun updateCostPrice(costPrice: Double) {
        _state.update { it.copy(costPrice = costPrice) }
    }

    private fun updateSku(sku: String) {
        _state.update { it.copy(sku = sku) }
    }

    private fun updateUnitOfMeasureCode(code: String) {
        _state.update { it.copy(unitOfMeasureCode = code) }
    }

    private fun updateCategoryCode(code: String) {
        _state.update { it.copy(categoryCode = code) }
    }

    private fun createProduct() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            createProduct(
                code = state.value.code,
                name = state.value.name,
                description = state.value.description,
                unitPrice = state.value.unitPrice ?: 0.0,
                costPrice = state.value.costPrice ?: 0.0,
                sku = state.value.sku,
                unitOfMeasureId = state.value.unitOfMeasureList.firstOrNull { it.code == state.value.unitOfMeasureCode }?.id,
                categoryId = state.value.categoryList.firstOrNull { it.code == state.value.categoryCode }?.id
            ).onSuccess {
                clearState()
                snackbarManager.showSnackbar(getString(Res.string.product_created))
            }.onFailure {
                snackbarManager.showErrorSnackbar(
                    getString(Res.string.error_creating_product), it
                )
            }
            _state.update { it.copy(loading = false) }
        }
    }


    private fun updateProduct() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            updateProduct(
                id = state.value.selectedProduct!!.id,
                code = state.value.code,
                name = state.value.name,
                description = state.value.description,
                unitPrice = state.value.unitPrice ?: 0.0,
                costPrice = state.value.costPrice ?: 0.0,
                sku = state.value.sku,
                unitOfMeasureId = state.value.unitOfMeasureList.firstOrNull { it.code == state.value.unitOfMeasureCode }?.id,
                categoryId = state.value.categoryList.firstOrNull { it.code == state.value.categoryCode }?.id
            ).onSuccess {
                snackbarManager.showSnackbar(getString(Res.string.product_updated))
            }.onFailure {
                snackbarManager.showErrorSnackbar(
                    getString(Res.string.error_updating_product), it
                )
            }
            _state.update { it.copy(loading = false) }
        }
    }

    private fun deleteProduct() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            deleteProduct(
                state.value.selectedProduct!!.id
            ).onSuccess {
                clearState()
                snackbarManager.showSnackbar(getString(Res.string.product_deleted))
            }.onFailure {
                snackbarManager.showErrorSnackbar(
                    getString(Res.string.error_deleting_product), it
                )
            }
            _state.update { it.copy(loading = false) }
        }
    }

    private fun clearState() {
        _state.update {
            it.copy(
                code = "",
                name = "",
                description = "",
                unitPrice = 0.0,
                costPrice = 0.0,
                sku = "",
                unitOfMeasureCode = "",
                categoryCode = ""
            )
        }
    }
}