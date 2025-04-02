package org.example.erp.features.inventory.presentation.unitOfMeasures

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.code
import erp.composeapp.generated.resources.description
import erp.composeapp.generated.resources.name
import org.example.erp.core.presentation.components.ItemGrid
import org.example.erp.core.presentation.components.LabeledTextField
import org.example.erp.core.presentation.components.SearchScreen
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
    SearchScreen(
        modifier = modifier,
        query = state.query,
        isSearchActive = state.isQueryActive,
        loading = state.loading,
        isNew = state.isNew,
        selectedItem = state.selectedUnitOfMeasure,
        onQueryChange = { onEvent(UnitOfMeasuresEvent.UpdateQuery(it)) },
        onSearch = { onEvent(UnitOfMeasuresEvent.Search(it)) },
        onSearchActiveChange = { onEvent(UnitOfMeasuresEvent.UpdateIsQueryActive(it)) },
        fetchUser = state.getUserById,
        onBack = onBack,
        onDelete = { onEvent(UnitOfMeasuresEvent.DeleteUnitOfMeasure) },
        onCreate = { onEvent(UnitOfMeasuresEvent.CreateUnitOfMeasure) },
        onUpdate = { onEvent(UnitOfMeasuresEvent.UpdateUnitOfMeasure) },
        searchResults = {
            ItemGrid(
                list = state.searchResults,
                onItemClick = {
                    onEvent(UnitOfMeasuresEvent.UpdateIsQueryActive(false))
                    onEvent(UnitOfMeasuresEvent.SearchUnitOfMeasure(it.code))
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
                        onValueChange = { onEvent(UnitOfMeasuresEvent.UpdateCode(it)) },
                        label = stringResource(Res.string.code),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions {
                            onEvent(UnitOfMeasuresEvent.SearchUnitOfMeasure(state.code))
                            defaultKeyboardAction(ImeAction.Next)
                        })
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
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                )
            }
        },
    )
}



