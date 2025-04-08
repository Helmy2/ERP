package org.example.erp.features.inventory.presentation.product

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
import erp.composeapp.generated.resources.category
import erp.composeapp.generated.resources.code
import erp.composeapp.generated.resources.cost_price
import erp.composeapp.generated.resources.description
import erp.composeapp.generated.resources.name
import erp.composeapp.generated.resources.sku
import erp.composeapp.generated.resources.unit_of_measures
import erp.composeapp.generated.resources.unit_price
import org.example.erp.core.presentation.components.ItemGrid
import org.example.erp.core.presentation.components.ItemPicker
import org.example.erp.core.presentation.components.LabeledTextField
import org.example.erp.core.presentation.components.SearchScreen
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductRoute(
    viewModel: ProductViewModel = koinViewModel(),
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ProductScreen(state = state, onEvent = viewModel::handleEvent, onBack, modifier)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductScreen(
    state: ProductState,
    onEvent: (ProductEvent) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SearchScreen(
        modifier = modifier,
        query = state.query,
        isSearchActive = state.isQueryActive,
        loading = state.loading,
        isNew = state.isNew,
        selectedItem = state.selectedProduct,
        onQueryChange = { onEvent(ProductEvent.UpdateQuery(it)) },
        onSearch = { onEvent(ProductEvent.Search(it)) },
        onSearchActiveChange = { onEvent(ProductEvent.UpdateIsQueryActive(it)) },
        fetchUser = state.getUserById,
        onBack = onBack,
        onDelete = { onEvent(ProductEvent.DeleteProduct) },
        onCreate = { onEvent(ProductEvent.CreateProduct) },
        onUpdate = { onEvent(ProductEvent.UpdateProduct) },
        searchResults = {
            ItemGrid(
                list = state.searchResults,
                onItemClick = {
                    onEvent(ProductEvent.UpdateIsQueryActive(false))
                    onEvent(ProductEvent.SearchProduct(it.code))
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
                        onValueChange = { onEvent(ProductEvent.UpdateCode(it)) },
                        label = stringResource(Res.string.code),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions {
                            onEvent(ProductEvent.SearchProduct(state.code))
                            defaultKeyboardAction(ImeAction.Next)
                        })
                    Spacer(modifier = Modifier.size(8.dp))
                    LabeledTextField(
                        value = state.name,
                        onValueChange = { onEvent(ProductEvent.UpdateName(it)) },
                        label = stringResource(Res.string.name),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                    )
                }
                FlowRow {
                    LabeledTextField(
                        value = if (state.unitPrice == null) "" else state.unitPrice.toString(),
                        onValueChange = {
                            it.toDoubleOrNull()?.let { price ->
                                onEvent(ProductEvent.UpdateUnitPrice(price))
                            }
                        },
                        label = stringResource(Res.string.unit_price),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    LabeledTextField(
                        value = if (state.costPrice == null) "" else state.costPrice.toString(),
                        onValueChange = {
                            it.toDoubleOrNull()?.let { price ->
                                onEvent(ProductEvent.UpdateUnitPrice(price))
                            }
                        },
                        label = stringResource(Res.string.cost_price),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                    )

                }
                FlowRow {
                    LabeledTextField(
                        value = state.sku,
                        onValueChange = { onEvent(ProductEvent.UpdateSku(it)) },
                        label = stringResource(Res.string.sku),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    LabeledTextField(
                        value = state.description,
                        onValueChange = { onEvent(ProductEvent.UpdateDescription(it)) },
                        label = stringResource(Res.string.description),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                    )
                }

                FlowRow {
                    ItemPicker(
                        label = stringResource(Res.string.category),
                        itemCode = state.categoryCode,
                        forbiddenItemCodes = emptyList(),
                        onItemCodeChanged = {
                            it?.let {
                                onEvent(ProductEvent.UpdateCategoryCode(it))
                            }
                        },
                        availableItems = state.categoryList,
                        isDialogVisible = state.isCategoryDialogOpen,
                        onDialogVisibilityChanged = {
                            onEvent(ProductEvent.UpdateIsCategoryDialogOpen(it))
                        },
                        onItemClicked = {
                            it?.let {
                                onEvent(ProductEvent.UpdateCategoryCode(it.code))
                            }
                        },
                        itemLabel = { "${it.code}: ${it.name}" },
                        matchesItemCode = { code, item -> item.code == code },
                        onAddNewItem = {
                            onEvent(ProductEvent.SearchProduct(it))
                        },
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    ItemPicker(
                        label = stringResource(Res.string.unit_of_measures),
                        itemCode = state.unitOfMeasureCode,
                        forbiddenItemCodes = emptyList(),
                        onItemCodeChanged = {
                            it?.let {
                                onEvent(ProductEvent.UpdateUnitOfMeasureCode(it))
                            }
                        },
                        availableItems = state.unitOfMeasureList,
                        isDialogVisible = state.isUnitOfMeasureDialogOpen,
                        onDialogVisibilityChanged = {
                            onEvent(ProductEvent.UpdateIsUnitOfMeasureDialogOpen(it))
                        },
                        onItemClicked = {
                            it?.let {
                                onEvent(ProductEvent.UpdateUnitOfMeasureCode(it.code))
                            }
                        },
                        itemLabel = { "${it.code}: ${it.name}" },
                        matchesItemCode = { code, item -> item.code == code },
                        onAddNewItem = {
                            onEvent(ProductEvent.SearchProduct(it))
                        },
                    )
                }
            }
        })
}
