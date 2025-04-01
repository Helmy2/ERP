package org.example.erp.features.inventory.presentation.category

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.code
import erp.composeapp.generated.resources.create
import erp.composeapp.generated.resources.created_by
import erp.composeapp.generated.resources.delete
import erp.composeapp.generated.resources.name
import erp.composeapp.generated.resources.no_categories_found
import erp.composeapp.generated.resources.none
import erp.composeapp.generated.resources.parent_category
import erp.composeapp.generated.resources.update
import erp.composeapp.generated.resources.updated_by
import org.example.erp.core.presentation.components.BackButton
import org.example.erp.core.presentation.components.ItemGrid
import org.example.erp.core.presentation.components.LabeledTextField
import org.example.erp.core.util.toLocalString
import org.example.erp.features.inventory.domain.entity.Category
import org.example.erp.features.inventory.presentation.components.VersionDetails
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CategoryRoute(
    onBack: () -> Unit,
    viewModel: CategoryViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CategoryScreen(state = state, onEvent = viewModel::handleEvent, onBack = onBack, modifier)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryScreen(
    state: CategoryState,
    onEvent: (CategoryEvent) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.verticalScroll(rememberScrollState()).padding(16.dp)
        ) {
            ItemGrid(
                list = state.categories,
                onItemClick = { onEvent(CategoryEvent.SearchCategory(it.code)) },
                labelProvider = { "${it.code} - ${it.name}" },
                isSelected = { it.code == state.code },
                modifier = Modifier.heightIn(max = 300.dp).verticalScroll(rememberScrollState())
            )

            FlowRow {
                LabeledTextField(
                    value = state.code,
                    onValueChange = { onEvent(CategoryEvent.UpdateCode(it)) },
                    label = stringResource(Res.string.code),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions {
                        onEvent(CategoryEvent.SearchCategory(state.code))
                        defaultKeyboardAction(ImeAction.Next)
                    })
                Spacer(modifier = Modifier.size(8.dp))
                LabeledTextField(
                    value = state.name,
                    onValueChange = { onEvent(CategoryEvent.UpdateName(it)) },
                    label = stringResource(Res.string.name),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )
            }

            FlowRow {
                D(
                    code = state.parentCategoryCode,
                    categoryList = state.categories.filterNot { it.id == state.selectedCategory?.id },
                    expended = state.isParentCategoryOpen,
                    onDismissRequest = { onEvent(CategoryEvent.UpdateIsParentCategoryOpen(it)) },
                    onItemClick = { onEvent(CategoryEvent.UpdateParentCategoryCode(it)) },
                    modifier = Modifier.heightIn(max = 300.dp).verticalScroll(rememberScrollState())
                )
            }

            VersionDetails(
                sectionHeader = stringResource(Res.string.created_by),
                modifierName = state.selectedCategory?.createdBy,
                getDisplayNameForUser = state.getDisplayNameForUser,
                modificationTimestamp = state.selectedCategory?.createdAt?.toLocalString(),
            )
            VersionDetails(
                sectionHeader = stringResource(Res.string.updated_by),
                modifierName = state.selectedCategory?.updatedBy,
                getDisplayNameForUser = state.getDisplayNameForUser,
                modificationTimestamp = state.selectedCategory?.updatedAt?.toLocalString(),
            )
            Row(
                modifier = Modifier.padding(8.dp).align(Alignment.End),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)

            ) {
                ElevatedButton(
                    onClick = {
                        onEvent(CategoryEvent.DeleteCategory)
                    },
                    enabled = !state.isNew && !state.loading,
                ) {
                    Text(stringResource(Res.string.delete))
                }
                Button(
                    onClick = {
                        if (state.isNew) {
                            onEvent(CategoryEvent.CreateCategory)
                        } else {
                            onEvent(CategoryEvent.UpdateCategory)
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
            onClick = onBack, modifier = Modifier.padding(16.dp).align(Alignment.TopStart)
        )
    }
}

@Composable
fun D(
    code: String?,
    expended: Boolean,
    categoryList: List<Category>,
    onDismissRequest: (Boolean) -> Unit,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        LabeledTextField(
            value = code ?: "",
            onValueChange = onItemClick,
            label = stringResource(Res.string.parent_category),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        Card(
            onClick = {
                onDismissRequest(true)
            },
            content = {
                Text(
                    text = if (code.isNullOrEmpty()) stringResource(Res.string.none) else categoryList.firstOrNull { it.code == code }?.name
                        ?: stringResource(Res.string.no_categories_found),
                    modifier = Modifier.padding(8.dp)
                )
            },
            modifier = Modifier.padding(8.dp),
        )
    }


    AnimatedVisibility(expended) {
        Dialog(
            onDismissRequest = { onDismissRequest(false) },
        ) {
            ItemGrid(
                list = categoryList,
                onItemClick = {
                    onDismissRequest(false)
                    onItemClick(it.code)
                },
                labelProvider = { "${it.code} - ${it.name}" },
                isSelected = { it.code == code },
                modifier = Modifier.heightIn(max = 300.dp).verticalScroll(rememberScrollState())
            )
        }
    }
}
