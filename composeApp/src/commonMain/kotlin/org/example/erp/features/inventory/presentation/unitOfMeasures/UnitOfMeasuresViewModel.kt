package org.example.erp.features.inventory.presentation.unitOfMeasures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.error_creating_unit_of_measure
import erp.composeapp.generated.resources.error_deleting_unit_of_measure
import erp.composeapp.generated.resources.error_updating_unit_of_measure
import erp.composeapp.generated.resources.unit_of_measure_created
import erp.composeapp.generated.resources.unit_of_measure_deleted
import erp.composeapp.generated.resources.unit_of_measure_updated
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.erp.core.domain.snackbar.SnackbarManager
import org.example.erp.features.inventory.domain.useCase.unitOfMeasures.CreateUnitOfMeasureUseCase
import org.example.erp.features.inventory.domain.useCase.unitOfMeasures.DeleteUnitOfMeasureUseCase
import org.example.erp.features.inventory.domain.useCase.unitOfMeasures.GetAllUnitsOfMeasureUseCase
import org.example.erp.features.inventory.domain.useCase.unitOfMeasures.UpdateUnitOfMeasureUseCase
import org.example.erp.features.user.domain.usecase.GetDisplayNameUseCase
import org.jetbrains.compose.resources.getString

class UnitOfMeasuresViewModel(
    private val getDisplayName: GetDisplayNameUseCase,
    private val snackbarManager: SnackbarManager,
    private val getAllUnitsOfMeasure: GetAllUnitsOfMeasureUseCase,
    private val createUnitOfMeasure: CreateUnitOfMeasureUseCase,
    private val deleteUnitOfMeasure: DeleteUnitOfMeasureUseCase,
    private val updateUnitOfMeasure: UpdateUnitOfMeasureUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(
        UnitOfMeasuresState(
            getDisplayNameForUser = { getDisplayName(it) })
    )
    val state = _state.onStart {
        leadInit()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = UnitOfMeasuresState(
            getDisplayNameForUser = { getDisplayName(it) })
    )

    private fun leadInit() {
        viewModelScope.launch {
            getAllUnitsOfMeasure().collect { list ->
                _state.update {
                    it.copy(
                        unitsOfMeasureList = list.sortedBy { measure -> measure.code },
                        loading = false
                    )
                }
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
            is UnitOfMeasuresEvent.SearchUnitOfMeasure -> findUnitOfMeasureByCode(event.code)
        }
    }

    private fun updateName(name: String) {
        _state.update { it.copy(name = name) }
    }

    private fun updateDescription(description: String) {
        _state.update { it.copy(description = description) }
    }

    private fun updateCode(code: String) {
        _state.update { it.copy(code = code) }
    }

    private fun findUnitOfMeasureByCode(code: String) {
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

    private fun updateUnitOfMeasure() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            updateUnitOfMeasure(
                id = _state.value.selectedUnitOfMeasure!!.id,
                code = _state.value.code,
                name = _state.value.name,
                description = _state.value.description,
            ).onSuccess {
                clearState()
                snackbarManager.showSnackbar(getString(Res.string.unit_of_measure_updated))
            }.onFailure {
                snackbarManager.showErrorSnackbar(
                    getString(Res.string.error_updating_unit_of_measure),
                    it
                )
            }
            _state.update { it.copy(loading = false) }
        }
    }

    private fun deleteUnitOfMeasure() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            deleteUnitOfMeasure(_state.value.code).onSuccess {
                clearState()
                snackbarManager.showSnackbar(getString(Res.string.unit_of_measure_deleted))
            }.onFailure {
                snackbarManager.showErrorSnackbar(
                    getString(Res.string.error_deleting_unit_of_measure),
                    it
                )
            }
        }
    }

    private fun createUnitOfMeasure() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            createUnitOfMeasure(
                code = _state.value.code,
                name = _state.value.name,
                description = _state.value.description
            ).onSuccess {
                clearState()
                snackbarManager.showSnackbar(getString(Res.string.unit_of_measure_created))
            }.onFailure {
                snackbarManager.showErrorSnackbar(
                    getString(Res.string.error_creating_unit_of_measure),
                    it
                )
            }
            _state.update { it.copy(loading = false) }
        }
    }

    private fun clearState() {
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