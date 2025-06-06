package org.example.erp.features.inventory.presentation.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.category_created
import erp.composeapp.generated.resources.category_deleted
import erp.composeapp.generated.resources.category_updated
import erp.composeapp.generated.resources.error_creating_category
import erp.composeapp.generated.resources.error_deleting_category
import erp.composeapp.generated.resources.error_syncing_categories
import erp.composeapp.generated.resources.error_updating_category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.erp.core.domain.snackbar.SnackbarManager
import org.example.erp.features.inventory.domain.useCase.category.CreateCategoryUseCase
import org.example.erp.features.inventory.domain.useCase.category.DeleteCategoryUseCase
import org.example.erp.features.inventory.domain.useCase.category.GetAllCategoryUseCase
import org.example.erp.features.inventory.domain.useCase.category.GetCategoryByCodeUseCase
import org.example.erp.features.inventory.domain.useCase.category.GetCategoryByIdUseCase
import org.example.erp.features.inventory.domain.useCase.category.SyncCategoriesUseCase
import org.example.erp.features.inventory.domain.useCase.category.UpdateCategoryUseCase
import org.example.erp.features.user.domain.usecase.GetDisplayNameUseCase
import org.jetbrains.compose.resources.getString

class CategoryViewModel(
    private val getDisplayName: GetDisplayNameUseCase,
    private val snackbarManager: SnackbarManager,
    private val syncCategories: SyncCategoriesUseCase,
    private val getCategoryByCode: GetCategoryByCodeUseCase,
    private val getAllCategory: GetAllCategoryUseCase,
    private val createCategory: CreateCategoryUseCase,
    private val updateCategory: UpdateCategoryUseCase,
    private val deleteCategory: DeleteCategoryUseCase,
    private val getCategoryById: GetCategoryByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        CategoryState(getUserById = { getDisplayName(it) })
    )
    val state = _state.onStart {
        syncCategories().onFailure {
            snackbarManager.showErrorSnackbar(
                getString(Res.string.error_syncing_categories), it
            )
        }
        search("")
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = CategoryState(getUserById = { getDisplayName(it) })
    )

    private fun search(query: String) {
        viewModelScope.launch {
            getAllCategory(query).fold(onSuccess = { list ->
                _state.update {
                    it.copy(
                        categories = list.sortedBy { category -> category.code }, loading = false
                    )
                }
            }, onFailure = { throwable ->
                _state.update { it.copy(loading = false) }
                println("Exception in getAllCategory: $throwable")
            })
        }
    }


    fun handleEvent(event: CategoryEvent) {
        when (event) {
            is CategoryEvent.CreateCategory -> createCategory()
            is CategoryEvent.DeleteCategory -> deleteCategory()
            is CategoryEvent.SearchCategory -> searchCategory(event.code)
            is CategoryEvent.UpdateCategory -> updateCategory()
            is CategoryEvent.UpdateCode -> updateCode(event.code)
            is CategoryEvent.UpdateName -> updateName(event.name)
            is CategoryEvent.UpdateIsParentCategoryOpen -> updateIsParentCategoryOpen(event.open)
            is CategoryEvent.UpdateParentCategoryCode -> updateParentCategoryCode(event.code)
            is CategoryEvent.Search -> search(event.query)
            is CategoryEvent.UpdateIsQueryActive -> updateIsQueryActive(event.isQueryActive)
            is CategoryEvent.UpdateQuery -> updateQuery(event.query)
        }
    }

    private fun updateQuery(query: String) {
        _state.update { it.copy(query = query) }
        search(query)
    }

    private fun updateIsQueryActive(queryActive: Boolean) {
        _state.update { it.copy(isQueryActive = queryActive) }
    }

    private fun updateParentCategoryCode(code: String?) {
        _state.update { it.copy(parentCategoryCode = code) }
    }

    private fun updateIsParentCategoryOpen(open: Boolean) {
        _state.update { it.copy(isParentCategoryOpen = open) }
    }

    private fun updateName(name: String) {
        _state.update { it.copy(name = name) }
    }

    private fun updateCode(code: String) {
        _state.update { it.copy(code = code) }
    }

    private fun updateCategory() {
        viewModelScope.launch {
            val parentCode = state.value.categories.firstOrNull {
                it.code == state.value.parentCategoryCode
            }?.id.takeIf { it != state.value.selectedCategory?.id }
            updateCategory(
                id = state.value.selectedCategory!!.id,
                name = state.value.name,
                code = state.value.code,
                parentCategoryId = parentCode
            ).fold(onSuccess = {
                clearState()
                snackbarManager.showSnackbar(getString(Res.string.category_updated))
            }, onFailure = {
                snackbarManager.showErrorSnackbar(
                    getString(Res.string.error_updating_category), it
                )
            })
        }
    }

    private fun createCategory() {
        viewModelScope.launch {
            createCategory(
                name = state.value.name,
                code = state.value.code,
                parentCategoryId = state.value.categories.firstOrNull {
                    it.code == state.value.parentCategoryCode
                }?.id
            ).fold(onSuccess = {
                clearState()
                snackbarManager.showSnackbar(getString(Res.string.category_created))
            }, onFailure = {
                snackbarManager.showErrorSnackbar(
                    getString(Res.string.error_creating_category), it
                )
            })
        }
    }

    private fun deleteCategory() {
        viewModelScope.launch {
            deleteCategory(state.value.code).fold(onSuccess = {
                clearState()
                snackbarManager.showSnackbar(getString(Res.string.category_deleted))
            }, onFailure = {
                snackbarManager.showErrorSnackbar(
                    getString(Res.string.error_deleting_category), it
                )
            })
        }
    }

    private fun searchCategory(code: String) {
        viewModelScope.launch {
            _state.update { it.copy(code = code, loading = true) }

            getCategoryByCode(code).fold(onFailure = {
                _state.update {
                    it.copy(
                        name = "",
                        selectedCategory = null,
                        loading = false,
                        isParentCategoryOpen = false,
                        parentCategoryCode = ""
                    )
                }
            }, onSuccess = { category ->
                _state.update {
                    it.copy(
                        name = category.name,
                        selectedCategory = category,
                        parentCategoryCode = getCategoryById(
                            category.parentCategoryId ?: ""
                        ).getOrNull()?.code,
                        loading = false
                    )
                }
            })
        }
    }

    private fun clearState() {
        search("")
        _state.update {
            it.copy(
                loading = false,
                selectedCategory = null,
                code = "",
                name = "",
                parentCategoryCode = "",
                isParentCategoryOpen = false
            )
        }
    }
}