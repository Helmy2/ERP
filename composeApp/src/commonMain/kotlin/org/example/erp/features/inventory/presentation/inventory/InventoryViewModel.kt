package org.example.erp.features.inventory.presentation.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import org.example.erp.core.domain.navigation.Destination
import org.example.erp.core.domain.navigation.Navigator

class InventoryViewModel(
    private val navigator: Navigator,
) : ViewModel() {

    private val _state = MutableStateFlow(InventoryState())
    val state = _state
        .onStart {
            /** Load initial data here **/

        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = InventoryState()
        )

    fun handleEvent(event: InventoryEvent) {
        when (event) {
            InventoryEvent.NavigateToUnitOfMeasures -> navigator.navigate(Destination.UnitOfMeasures)
        }
    }
}