package org.example.erp.features.inventory.presentation.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.category_created
import erp.composeapp.generated.resources.category_deleted
import erp.composeapp.generated.resources.category_updated
import erp.composeapp.generated.resources.error_creating_category
import erp.composeapp.generated.resources.error_deleting_category
import erp.composeapp.generated.resources.error_updating_category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.erp.core.domain.snackbar.SnackbarManager
import org.example.erp.features.inventory.domain.entity.Category
import org.example.erp.features.inventory.domain.useCase.category.CreateCategoryUseCase
import org.example.erp.features.inventory.domain.useCase.category.DeleteCategoryUseCase
import org.example.erp.features.inventory.domain.useCase.category.GetAllCategoryUseCase
import org.example.erp.features.inventory.domain.useCase.category.UpdateCategoryUseCase
import org.example.erp.features.user.domain.usecase.GetDisplayNameUseCase
import org.jetbrains.compose.resources.getString

class CategoryViewModel(
    private val getDisplayName: GetDisplayNameUseCase,
    private val snackbarManager: SnackbarManager,
    private val getAllCategoryUseCase: GetAllCategoryUseCase,
    private val createCategoryUseCase: CreateCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(
        CategoryState(getDisplayNameForUser = { getDisplayName(it) })
    )
    val state = _state.onStart {
        leadInit()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = CategoryState(getDisplayNameForUser = { getDisplayName(it) })
    )

    private fun leadInit() {
        viewModelScope.launch {
            getAllCategoryUseCase().collect { result ->
                result.fold(onSuccess = { list ->
                    _state.update {
                        it.copy(
                            categories = list.sortedBy { category -> category.code },
                            loading = false
                        )
                    }
                }, onFailure = { throwable ->
                    _state.update { it.copy(loading = false) }
                    println("Exception in getAllCategory: $throwable")
                })
            }
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
            is CategoryEvent.UpdateParentCategory -> updateParentCategory(event.category)
        }
    }

    private fun updateParentCategory(category: Category) {
        _state.update { it.copy(parentCategory = category) }
    }

    private fun updateName(name: String) {
        _state.update { it.copy(name = name) }
    }

    private fun updateCode(code: String) {
        _state.update { it.copy(code = code) }
    }

    private fun updateCategory() {
        viewModelScope.launch {
            updateCategoryUseCase(
                id = state.value.selectedCategory!!.id,
                name = state.value.name,
                code = state.value.code,
                parentCategoryId = state.value.parentCategory?.id
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
            createCategoryUseCase(
                name = state.value.name,
                code = state.value.code,
                parentCategoryId = state.value.parentCategory?.id
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
            deleteCategoryUseCase(state.value.selectedCategory!!.id).fold(onSuccess = {
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
        _state.update { it.copy(code = code, loading = true) }

        val category = _state.value.categories.firstOrNull { item ->
            item.code == code
        }

        if (category == null) {
            _state.update {
                it.copy(
                    name = "", parentCategory = null, selectedCategory = null, loading = false
                )
            }
        } else {
            _state.update {
                it.copy(
                    name = category.name,
                    parentCategory = category.parentCategory,
                    selectedCategory = category,
                    loading = false
                )
            }
        }
    }

    private fun clearState() {
        _state.update {
            it.copy(
                loading = false,
                selectedCategory = null,
                code = "",
                name = "",
                parentCategory = null
            )
        }
    }
}