package org.example.erp.features.inventory.presentation.warehouses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.error_creating_warehouse
import erp.composeapp.generated.resources.error_deleting_warehouse
import erp.composeapp.generated.resources.error_updating_warehouse
import erp.composeapp.generated.resources.warehouse_created
import erp.composeapp.generated.resources.warehouse_deleted
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.erp.core.domain.snackbar.SnackbarManager
import org.example.erp.features.inventory.domain.useCase.unitOfMeasures.DeleteUnitOfMeasureUseCase
import org.example.erp.features.inventory.domain.useCase.warehouse.CreateWarehouseUseCase
import org.example.erp.features.inventory.domain.useCase.warehouse.GetAllWarehouseUseCase
import org.example.erp.features.inventory.domain.useCase.warehouse.GetWarehouseByCodeUseCase
import org.example.erp.features.inventory.domain.useCase.warehouse.SyncWarehouseUseCase
import org.example.erp.features.inventory.domain.useCase.warehouse.UpdateWarehouseUseCase
import org.example.erp.features.user.domain.usecase.GetDisplayNameUseCase
import org.jetbrains.compose.resources.getString

class WarehouseViewModel(
    private val getDisplayName: GetDisplayNameUseCase,
    private val snackbarManager: SnackbarManager,
    private val syncWarehouse: SyncWarehouseUseCase,
    private val getWarehouseByCode: GetWarehouseByCodeUseCase,
    private val getAllWarehouse: GetAllWarehouseUseCase,
    private val createWarehouse: CreateWarehouseUseCase,
    private val updateWarehouse: UpdateWarehouseUseCase,
    private val deleteWarehouse: DeleteUnitOfMeasureUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(
        WarehouseState(
            getUserById = { getDisplayName(it) },
        )
    )

    val state = _state.onStart {
        syncWarehouse()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = WarehouseState(
            getUserById = { getDisplayName(it) })
    )

    private fun search(query: String) {
        viewModelScope.launch {
            getAllWarehouse(query).fold(onSuccess = { list ->
                _state.update {
                    it.copy(
                        searchResults = list.sortedBy { warehouse -> warehouse.code },
                        loading = false
                    )
                }
            }, onFailure = {
                _state.update { it.copy(loading = false) }
                println("Exception in getAllWarehouse: $it")
            })
        }
    }

    fun handleEvent(event: WarehouseEvent) {
        when (event) {
            is WarehouseEvent.CreateWarehouse -> createWarehouse()
            is WarehouseEvent.DeleteWarehouse -> deleteWarehouse()
            is WarehouseEvent.SearchWarehouse -> searchWarehouse(event.code)
            is WarehouseEvent.UpdateCapacity -> updateCapacity(event.capacity)
            is WarehouseEvent.UpdateCode -> updateCode(event.code)
            is WarehouseEvent.UpdateLocation -> updateLocation(event.location)
            is WarehouseEvent.UpdateName -> updateName(event.name)
            is WarehouseEvent.UpdateWarehouse -> updateWarehouse()
            is WarehouseEvent.Search -> search(event.query)
            is WarehouseEvent.UpdateIsQueryActive -> updateIsQueryActive(event.isQueryActive)
            is WarehouseEvent.UpdateQuery -> updateQuery(event.query)
        }
    }

    private fun updateQuery(query: String) {
        _state.update { it.copy(query = query) }
        search(query)
    }

    private fun updateIsQueryActive(queryActive: Boolean) {
        _state.update { it.copy(isQueryActive = queryActive) }
    }

    private fun searchWarehouse(code: String) {
        viewModelScope.launch {
            _state.update { it.copy(code = code, loading = true) }

            getWarehouseByCode(code).fold(onFailure = {
                _state.update {
                    it.copy(
                        name = "",
                        capacity = null,
                        location = "",
                        selectedWarehouse = null,
                        loading = false
                    )
                }
            }, onSuccess = { warehouses ->
                _state.update {
                    it.copy(
                        name = warehouses.name,
                        capacity = warehouses.capacity,
                        location = warehouses.location,
                        selectedWarehouse = warehouses,
                        loading = false
                    )
                }
            })
        }
    }

    private fun deleteWarehouse() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            deleteWarehouse(_state.value.code).onSuccess {
                clearState()
                snackbarManager.showSnackbar(getString(Res.string.warehouse_deleted))
            }.onFailure {
                snackbarManager.showErrorSnackbar(
                    getString(Res.string.error_deleting_warehouse), it
                )
            }
            _state.update { it.copy(loading = false) }
        }
    }

    private fun createWarehouse() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            createWarehouse(
                code = _state.value.code,
                name = _state.value.name,
                capacity = _state.value.capacity,
                location = _state.value.location
            ).onSuccess {
                clearState()
                snackbarManager.showSnackbar(getString(Res.string.warehouse_created))
            }.onFailure {
                snackbarManager.showErrorSnackbar(
                    getString(Res.string.error_creating_warehouse), it
                )
            }
            _state.update { it.copy(loading = false) }
        }
    }

    private fun updateCapacity(capacity: Long) {
        _state.update { it.copy(capacity = capacity) }
    }

    private fun updateLocation(location: String) {
        _state.update { it.copy(location = location) }
    }

    private fun updateName(name: String) {
        _state.update { it.copy(name = name) }
    }

    private fun updateCode(code: String) {
        _state.update { it.copy(code = code) }
    }

    private fun updateWarehouse() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            updateWarehouse(
                id = _state.value.selectedWarehouse!!.id,
                code = _state.value.code,
                name = _state.value.name,
                capacity = _state.value.capacity,
                location = _state.value.location
            ).onSuccess {
                clearState()
                snackbarManager.showSnackbar(getString(Res.string.warehouse_created))
            }.onFailure {
                snackbarManager.showErrorSnackbar(
                    getString(Res.string.error_updating_warehouse), it
                )
            }
            _state.update { it.copy(loading = false) }
        }
    }

    private fun clearState() {
        _state.update {
            it.copy(
                loading = false,
                selectedWarehouse = null,
                code = "",
                name = "",
                capacity = 0,
                location = ""
            )
        }
    }
}