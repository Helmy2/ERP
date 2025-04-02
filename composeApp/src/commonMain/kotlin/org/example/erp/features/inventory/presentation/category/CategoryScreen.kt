package org.example.erp.features.inventory.presentation.category

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
import erp.composeapp.generated.resources.name
import erp.composeapp.generated.resources.parent_category
import org.example.erp.core.presentation.components.ItemGrid
import org.example.erp.core.presentation.components.ItemPicker
import org.example.erp.core.presentation.components.LabeledTextField
import org.example.erp.core.presentation.components.SearchScreen
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
    SearchScreen(
        modifier = modifier,
        query = state.query,
        isSearchActive = state.isQueryActive,
        loading = state.loading,
        isNew = state.isNew,
        selectedItem = state.selectedCategory,
        onQueryChange = { onEvent(CategoryEvent.UpdateQuery(it)) },
        onSearch = { onEvent(CategoryEvent.Search(it)) },
        onSearchActiveChange = { onEvent(CategoryEvent.UpdateIsQueryActive(it)) },
        fetchUser = state.getUserById,
        onBack = onBack,
        onDelete = { onEvent(CategoryEvent.DeleteCategory) },
        onCreate = { onEvent(CategoryEvent.CreateCategory) },
        onUpdate = { onEvent(CategoryEvent.UpdateCategory) },
        searchResults = {
            ItemGrid(
                list = state.categories,
                onItemClick = {
                    onEvent(CategoryEvent.UpdateIsQueryActive(false))
                    onEvent(CategoryEvent.SearchCategory(it.code))
                },
                labelProvider = { "${it.code}: ${it.name}" },
                isSelected = { it.code == state.code },
            )
        },
        mainContent = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
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

                ItemPicker(
                    label = stringResource(Res.string.parent_category),
                    itemCode = state.parentCategoryCode,
                    forbiddenItemCodes = state.forbiddenItemCodes,
                    onItemCodeChanged = { onEvent(CategoryEvent.UpdateParentCategoryCode(it)) },
                    availableItems = state.categories.filterNot { it.id == state.selectedCategory?.id },
                    isDialogVisible = state.isParentCategoryOpen,
                    onDialogVisibilityChanged = {
                        onEvent(
                            CategoryEvent.UpdateIsParentCategoryOpen(
                                it
                            )
                        )
                    },
                    onItemClicked = { onEvent(CategoryEvent.UpdateParentCategoryCode(it?.code)) },
                    itemLabel = { "${it.code}: ${it.name}" },
                    matchesItemCode = { code, category -> category.code == code },
                    onAddNewItem = { onEvent(CategoryEvent.SearchCategory(it)) },
                    modifier = Modifier
                )
            }
        },
    )
}