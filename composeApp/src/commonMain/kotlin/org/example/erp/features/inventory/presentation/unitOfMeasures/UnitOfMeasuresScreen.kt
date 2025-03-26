package org.example.erp.features.inventory.presentation.unitOfMeasures

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.code
import erp.composeapp.generated.resources.create
import erp.composeapp.generated.resources.created_by
import erp.composeapp.generated.resources.delete
import erp.composeapp.generated.resources.description
import erp.composeapp.generated.resources.name
import erp.composeapp.generated.resources.update
import erp.composeapp.generated.resources.updated_by
import org.example.erp.core.presentation.components.BackButton
import org.example.erp.core.presentation.components.LabeledTextField
import org.example.erp.core.util.toLocalString
import org.example.erp.features.inventory.presentation.components.VersionDetails
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UnitOfMeasuresRoute(
    viewModel: UnitOfMeasuresViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    UnitOfMeasuresScreen(state = state, onEvent = viewModel::handleEvent)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UnitOfMeasuresScreen(
    state: UnitOfMeasuresState,
    onEvent: (UnitOfMeasuresEvent) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),

    ) {
        BackButton(
            onClick = {
                onEvent(UnitOfMeasuresEvent.NavigateBack)
            },
            modifier = Modifier.align(
                Alignment.Start
            )
        )
        Spacer(modifier = Modifier.size(8.dp))
        FlowRow {
            LabeledTextField(
                value = state.code,
                onValueChange = { onEvent(UnitOfMeasuresEvent.UpdateCode(it)) },
                label = stringResource(Res.string.code),
            )
            Spacer(modifier = Modifier.size(8.dp))
            LabeledTextField(
                value = state.name,
                onValueChange = { onEvent(UnitOfMeasuresEvent.UpdateName(it)) },
                label = stringResource(Res.string.name),
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        LabeledTextField(
            value = state.description,
            onValueChange = { onEvent(UnitOfMeasuresEvent.UpdateDescription(it)) },
            label = stringResource(Res.string.description),
            numberOfLines = 5,
        )
        Spacer(modifier = Modifier.size(8.dp))
        VersionDetails(
            sectionHeader = stringResource(Res.string.created_by),
            modifierName = state.selectedUnitOfMeasure?.createdBy,
            modificationTimestamp = state.selectedUnitOfMeasure?.createdAt?.toLocalString(),
        )
        Spacer(modifier = Modifier.size(8.dp))
        VersionDetails(
            sectionHeader = stringResource(Res.string.updated_by),
            modifierName = state.selectedUnitOfMeasure?.updatedBy,
            modificationTimestamp = state.selectedUnitOfMeasure?.updatedAt?.toLocalString()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ElevatedButton(
                onClick = {
                    onEvent(UnitOfMeasuresEvent.DeleteUnitOfMeasure)
                },
                enabled = !state.isNewUnitOfMeasure && !state.loading,
            ) {
                Text(stringResource(Res.string.delete))
            }
            Button(
                onClick = {
                    if (state.isNewUnitOfMeasure) {
                        onEvent(UnitOfMeasuresEvent.CreateUnitOfMeasure)
                    } else {
                        onEvent(UnitOfMeasuresEvent.UpdateUnitOfMeasure)
                    }
                },
                enabled = !state.loading,
            ) {
                Text(stringResource(if (state.isNewUnitOfMeasure) Res.string.create else Res.string.update))
            }
        }
    }
}

