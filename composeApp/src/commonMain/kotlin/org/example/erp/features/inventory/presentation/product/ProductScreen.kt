package org.example.erp.features.inventory.presentation.product

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.erp.core.presentation.components.ItemGrid
import org.example.erp.core.presentation.components.SearchScreen
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
        mainContent = {}
    )
}
