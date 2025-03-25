package org.example.erp.features.inventory.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun InventoryRoute(
    viewModel: InventoryViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    InventoryScreen(state = state, onEvent = viewModel::handleEvent)
}

@Composable
fun InventoryScreen(
    state: InventoryState,
    onEvent: (InventoryEvent) -> Unit,
) {

}
