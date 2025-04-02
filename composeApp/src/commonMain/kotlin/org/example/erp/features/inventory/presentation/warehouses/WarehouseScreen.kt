package org.example.erp.features.inventory.presentation.warehouses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.capacity
import erp.composeapp.generated.resources.code
import erp.composeapp.generated.resources.location
import erp.composeapp.generated.resources.name
import org.example.erp.core.presentation.components.ItemGrid
import org.example.erp.core.presentation.components.LabeledTextField
import org.example.erp.core.presentation.components.SearchScreen
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WarehouseRoute(
    onBack: () -> Unit,
    viewModel: WarehouseViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    WarehouseScreen(state = state, onEvent = viewModel::handleEvent, onBack = onBack, modifier)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WarehouseScreen(
    state: WarehouseState,
    onEvent: (WarehouseEvent) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SearchScreen(
        modifier = modifier,
        query = state.query,
        isSearchActive = state.isQueryActive,
        loading = state.loading,
        isNew = state.isNew,
        selectedItem = state.selectedWarehouse,
        onQueryChange = { onEvent(WarehouseEvent.UpdateQuery(it)) },
        onSearch = { onEvent(WarehouseEvent.SearchWarehouse(it)) },
        onSearchActiveChange = { onEvent(WarehouseEvent.UpdateIsQueryActive(it)) },
        fetchUser = state.getUserById,
        onBack = onBack,
        onDelete = { onEvent(WarehouseEvent.DeleteWarehouse) },
        onCreate = { onEvent(WarehouseEvent.CreateWarehouse) },
        onUpdate = { onEvent(WarehouseEvent.UpdateWarehouse) },
        searchResults = {
            ItemGrid(
                list = state.searchResults,
                onItemClick = {
                    onEvent(WarehouseEvent.UpdateIsQueryActive(false))
                    onEvent(WarehouseEvent.SearchWarehouse(it.code))
                },
                labelProvider = { "${it.code}: ${it.name}" },
                isSelected = { it.code == state.code },
            )
        },
        mainContent = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
            ) {
                FlowRow {
                    LabeledTextField(
                        value = state.code,
                        onValueChange = { onEvent(WarehouseEvent.UpdateCode(it)) },
                        label = stringResource(Res.string.code),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions {
                            onEvent(WarehouseEvent.SearchWarehouse(state.code))
                            defaultKeyboardAction(ImeAction.Next)
                        })
                    Spacer(modifier = Modifier.size(8.dp))
                    LabeledTextField(
                        value = state.name,
                        onValueChange = { onEvent(WarehouseEvent.UpdateName(it)) },
                        label = stringResource(Res.string.name),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                    )
                }
                FlowRow {
                    LabeledTextField(
                        value = (state.capacity ?: "").toString(),
                        onValueChange = {
                            val value = it.toLongOrNull()
                            if (value != null) {
                                onEvent(WarehouseEvent.UpdateCapacity(value))
                            }
                        },
                        label = stringResource(Res.string.capacity),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next, keyboardType = KeyboardType.Number
                        ),
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    LabeledTextField(
                        value = state.location,
                        onValueChange = { onEvent(WarehouseEvent.UpdateLocation(it)) },
                        label = stringResource(Res.string.location),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                    )
                }
            }
        }
    )
}
