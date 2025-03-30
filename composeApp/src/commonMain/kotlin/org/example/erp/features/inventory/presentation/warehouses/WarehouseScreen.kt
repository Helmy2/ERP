package org.example.erp.features.inventory.presentation.warehouses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.capacity
import erp.composeapp.generated.resources.code
import erp.composeapp.generated.resources.create
import erp.composeapp.generated.resources.created_by
import erp.composeapp.generated.resources.delete
import erp.composeapp.generated.resources.location
import erp.composeapp.generated.resources.name
import erp.composeapp.generated.resources.update
import erp.composeapp.generated.resources.updated_by
import org.example.erp.core.presentation.components.BackButton
import org.example.erp.core.presentation.components.ItemGrid
import org.example.erp.core.presentation.components.LabeledTextField
import org.example.erp.core.util.toLocalString
import org.example.erp.features.inventory.presentation.components.VersionDetails
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
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.verticalScroll(rememberScrollState()).padding(16.dp)
        ) {
            ItemGrid(
                list = state.warehousesList,
                onItemClick = { onEvent(WarehouseEvent.SearchWarehouse(it.code)) },
                labelProvider = { "${it.code} - ${it.name}" },
                isSelected = { it.code == state.code },
                modifier = Modifier.heightIn(max = 300.dp).verticalScroll(rememberScrollState())
            )

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
            VersionDetails(
                sectionHeader = stringResource(Res.string.created_by),
                modifierName = state.selectedWarehouse?.createdBy,
                getDisplayNameForUser = state.getDisplayNameForUser,
                modificationTimestamp = state.selectedWarehouse?.createdAt?.toLocalString(),
            )
            VersionDetails(
                sectionHeader = stringResource(Res.string.updated_by),
                modifierName = state.selectedWarehouse?.updatedBy,
                getDisplayNameForUser = state.getDisplayNameForUser,
                modificationTimestamp = state.selectedWarehouse?.updatedAt?.toLocalString(),
            )
            Row(
                modifier = Modifier.padding(8.dp).align(Alignment.End),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)

            ) {
                ElevatedButton(
                    onClick = {
                        onEvent(WarehouseEvent.DeleteWarehouse)
                    },
                    enabled = !state.isNew && !state.loading,
                ) {
                    Text(stringResource(Res.string.delete))
                }
                Button(
                    onClick = {
                        if (state.isNew) {
                            onEvent(WarehouseEvent.CreateWarehouse)
                        } else {
                            onEvent(WarehouseEvent.UpdateWarehouse)
                        }
                    },
                    enabled = !state.loading,
                ) {
                    Text(
                        stringResource(
                            if (state.isNew) Res.string.create
                            else Res.string.update
                        )
                    )
                }
            }
        }
        BackButton(
            onClick = onBack,
            modifier = Modifier.padding(16.dp).align(Alignment.TopStart)
        )
    }
}
