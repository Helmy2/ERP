package org.example.erp.features.inventory.presentation.inventory

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.example.erp.features.inventory.domain.entity.InventoryDestination

class InventoryViewModel : ViewModel() {

    private val _state = MutableStateFlow(
        InventoryState()
    )
    val state = _state

    fun handleEvent(event: InventoryEvent) {
        when (event) {
            is InventoryEvent.UpdateSelectDestination -> updateSelectDestination(event.destination)
        }
    }

    private fun updateSelectDestination(destination: InventoryDestination) {
        _state.update { it.copy(selectedDestination = destination) }
    }
}