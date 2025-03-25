package org.example.erp.features.inventory.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import org.example.erp.features.inventory.domain.repository.InventoryReps

class InventoryViewModel(
    val repository: InventoryReps
) : ViewModel() {

    private val _state = MutableStateFlow(InventoryState())
    val state = _state
        .onStart {
            /** Load initial data here **/
            repository.test()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = InventoryState()
        )

    fun handleEvent(event: InventoryEvent) {
        when (event) {
            else -> TODO("Handle events")
        }
    }
}