package org.example.erp.features.inventory.presentation.unitOfMeasures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.erp.core.domain.navigation.Navigator
import org.example.erp.core.domain.snackbar.SnackbarManager
import org.example.erp.features.inventory.domain.repository.InventoryReps

class UnitOfMeasuresViewModel(
    private val repository: InventoryReps,
    private val snackbarManager: SnackbarManager,
    private val navigator: Navigator
) : ViewModel() {

    private val _state = MutableStateFlow(UnitOfMeasuresState())
    val state = _state.onStart {
        leadInit()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = UnitOfMeasuresState()
    )

    private fun leadInit() {
        viewModelScope.launch {
            repository.getAllUnitsOfMeasure().collect { list ->
                _state.update { it.copy(unitsOfMeasureList = list.sortedBy { measure -> measure.code }, loading = false) }
            }
        }
    }

    fun handleEvent(event: UnitOfMeasuresEvent) {
        when (event) {
            is UnitOfMeasuresEvent.CreateUnitOfMeasure -> createUnitOfMeasure()
            is UnitOfMeasuresEvent.DeleteUnitOfMeasure -> deleteUnitOfMeasure()
            is UnitOfMeasuresEvent.UpdateUnitOfMeasure -> updateUnitOfMeasure()
            is UnitOfMeasuresEvent.UpdateCode -> updateCode(event.code)
            is UnitOfMeasuresEvent.UpdateDescription -> updateDescription(event.description)
            is UnitOfMeasuresEvent.UpdateName -> updateName(event.name)
            UnitOfMeasuresEvent.NavigateBack -> navigator.navigateBack()
        }
    }

    private fun updateName(name: String) {
        _state.update { it.copy(name = name) }
    }

    private fun updateDescription(description: String) {
        _state.update { it.copy(description = description) }
    }

    private fun updateCode(code: String) {
        viewModelScope.launch {
            _state.update { it.copy(code = code, loading = true) }

            val unitsOfMeasure = _state.value.unitsOfMeasureList.firstOrNull { item ->
                item.code == code
            }

            if (unitsOfMeasure == null) {
                _state.update {
                    it.copy(
                        name = "", description = "", selectedUnitOfMeasure = null, loading = false
                    )
                }

            } else {
                _state.update {
                    it.copy(
                        name = unitsOfMeasure.name,
                        description = unitsOfMeasure.description ?: "",
                        selectedUnitOfMeasure = unitsOfMeasure,
                        loading = false
                    )
                }
            }
        }
    }

    private fun updateUnitOfMeasure() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            repository.updateUnitOfMeasure(
                id = _state.value.selectedUnitOfMeasure!!.id,
                code = _state.value.code,
                name = _state.value.name,
                description = _state.value.description,
            ).onSuccess {
                clearState()
                snackbarManager.showSnackbar("Unit of measure updated")
            }.onFailure {
                snackbarManager.showErrorSnackbar("Error updating unit of measure", it)
            }
            _state.update { it.copy(loading = false) }
        }
    }

    private fun deleteUnitOfMeasure() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            repository.deleteUnitOfMeasure(_state.value.code).onSuccess {
                clearState()
                snackbarManager.showSnackbar("Unit of measure deleted")
            }.onFailure {
                snackbarManager.showErrorSnackbar("Error deleting unit of measure", it)
            }
        }
    }

    private fun createUnitOfMeasure() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            repository.createUnitOfMeasure(
                code = _state.value.code,
                name = _state.value.name,
                description = _state.value.description
            ).onSuccess {
                clearState()
                snackbarManager.showSnackbar("Unit of measure created")
            }.onFailure {
                snackbarManager.showErrorSnackbar("Error creating unit of measure", it)
            }
            _state.update { it.copy(loading = false) }
        }
    }

    private fun clearState(){
        _state.update {
            it.copy(
                loading = false,
                selectedUnitOfMeasure = null,
                code = "",
                name = "",
                description = ""
            )
        }
    }
}