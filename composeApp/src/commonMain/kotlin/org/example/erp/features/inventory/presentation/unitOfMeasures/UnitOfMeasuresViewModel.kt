package org.example.erp.features.inventory.presentation.unitOfMeasures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.error_creating_unit_of_measure
import erp.composeapp.generated.resources.error_deleting_unit_of_measure
import erp.composeapp.generated.resources.error_syncing_units_of_measure
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
import org.example.erp.features.inventory.domain.useCase.unitOfMeasures.GetUnitOfMeasuresByCodeUseCase
import org.example.erp.features.inventory.domain.useCase.unitOfMeasures.SyncUnitsOfMeasureUseCase
import org.example.erp.features.inventory.domain.useCase.unitOfMeasures.UpdateUnitOfMeasureUseCase
import org.example.erp.features.user.domain.usecase.GetDisplayNameUseCase
import org.jetbrains.compose.resources.getString

class UnitOfMeasuresViewModel(
    private val getDisplayName: GetDisplayNameUseCase,
    private val snackbarManager: SnackbarManager,
    private val syncUnitsOfMeasure: SyncUnitsOfMeasureUseCase,
    private val getUnitOfMeasureByCode: GetUnitOfMeasuresByCodeUseCase,
    private val getAllUnitsOfMeasure: GetAllUnitsOfMeasureUseCase,
    private val createUnitOfMeasure: CreateUnitOfMeasureUseCase,
    private val deleteUnitOfMeasure: DeleteUnitOfMeasureUseCase,
    private val updateUnitOfMeasure: UpdateUnitOfMeasureUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(
        UnitOfMeasuresState(
            getUserById = { getDisplayName(it) })
    )

    val state = _state.onStart {
        syncUnitsOfMeasure().onFailure {
            snackbarManager.showErrorSnackbar(
                getString(Res.string.error_syncing_units_of_measure), it
            )
        }
        search("")
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = UnitOfMeasuresState(
            getUserById = { getDisplayName(it) })
    )

    private fun search(query: String) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            getAllUnitsOfMeasure(query).fold(onSuccess = { list ->
                _state.update { it.copy(searchResults = list, loading = false) }
            }, onFailure = {
                _state.update { measuresState ->
                    measuresState.copy(
                        searchResults = emptyList(), loading = false
                    )
                }
                println("Exception in getAllUnitsOfMeasure: $it")
            })
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
            is UnitOfMeasuresEvent.Search -> search(event.query)
            is UnitOfMeasuresEvent.UpdateIsQueryActive -> updateIsQueryActive(event.isQueryActive)
            is UnitOfMeasuresEvent.UpdateQuery -> updateQuery(event.query)
        }
    }

    private fun updateQuery(query: String) {
        _state.update { it.copy(query = query) }
        search(query)
    }

    private fun updateIsQueryActive(queryActive: Boolean) {
        _state.update { it.copy(isQueryActive = queryActive) }
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
        viewModelScope.launch {
            _state.update { it.copy(code = code, loading = true) }

            getUnitOfMeasureByCode(code).fold(onFailure = {
                _state.update {
                    it.copy(
                        name = "",
                        description = "",
                        selectedUnitOfMeasure = null,
                        loading = false
                    )
                }
            }, onSuccess = { unitOfMeasure ->
                _state.update {
                    it.copy(
                        name = unitOfMeasure.name,
                        description = unitOfMeasure.description ?: "",
                        selectedUnitOfMeasure = unitOfMeasure,
                        loading = false
                    )
                }
            })
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
                    getString(Res.string.error_updating_unit_of_measure), it
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
                    getString(Res.string.error_deleting_unit_of_measure), it
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
                    getString(Res.string.error_creating_unit_of_measure), it
                )
            }
            _state.update { it.copy(loading = false) }
        }
    }

    private fun clearState() {
        search("")
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