package org.example.erp.features.inventory.presentation.inventory

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
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
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 250.dp),
    ) {
        item {
            Card(
                onClick = {
                    onEvent(InventoryEvent.NavigateToUnitOfMeasures)
                },
                modifier = Modifier.padding(16.dp),
            ) {
                Text(
                    "Unit of measures",
                    modifier = Modifier.padding(16.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}
