package org.example.erp.features.inventory.presentation.unitOfMeasures

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
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
    onBack: () -> Unit,
    viewModel: UnitOfMeasuresViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    UnitOfMeasuresScreen(state = state, onEvent = viewModel::handleEvent, onBack = onBack, modifier)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UnitOfMeasuresScreen(
    state: UnitOfMeasuresState,
    onEvent: (UnitOfMeasuresEvent) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BackButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.Start)
        )
        AnimatedVisibility(
            state.unitsOfMeasureList.isNotEmpty(),
            modifier = Modifier.fillMaxHeight(.2f)
                .verticalScroll(rememberScrollState())
        ) {
            FlowRow() {
                state.unitsOfMeasureList.forEach {
                    Card(
                        modifier = Modifier.padding(8.dp)
                            .sizeIn(maxWidth = 140.dp, maxHeight = 70.dp),
                        onClick = {
                            onEvent(UnitOfMeasuresEvent.UpdateCode(it.code))
                        }
                    ) {
                        Text(
                            "${it.code}: ${it.name}",
                            modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            FlowRow {
                LabeledTextField(
                    value = state.code,
                    onValueChange = { onEvent(UnitOfMeasuresEvent.UpdateCode(it)) },
                    label = stringResource(Res.string.code),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )
                Spacer(modifier = Modifier.size(8.dp))
                LabeledTextField(
                    value = state.name,
                    onValueChange = { onEvent(UnitOfMeasuresEvent.UpdateName(it)) },
                    label = stringResource(Res.string.name),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )
            }

            LabeledTextField(
                value = state.description,
                onValueChange = { onEvent(UnitOfMeasuresEvent.UpdateDescription(it)) },
                label = stringResource(Res.string.description),
                numberOfLines = 5,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            )

            VersionDetails(
                sectionHeader = stringResource(Res.string.created_by),
                modifierName = state.selectedUnitOfMeasure?.createdBy,
                modificationTimestamp = state.selectedUnitOfMeasure?.createdAt?.toLocalString(),
            )
            VersionDetails(
                sectionHeader = stringResource(Res.string.updated_by),
                modifierName = state.selectedUnitOfMeasure?.updatedBy,
                modificationTimestamp = state.selectedUnitOfMeasure?.updatedAt?.toLocalString()
            )
            Row(
                modifier = Modifier.padding(8.dp).align(Alignment.End),
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
}

