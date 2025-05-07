package org.example.erp.features.inventory.presentation.inventory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.categories
import erp.composeapp.generated.resources.products
import erp.composeapp.generated.resources.unit_of_measures
import erp.composeapp.generated.resources.warehouses
import kotlinx.coroutines.launch
import org.example.erp.core.presentation.components.ItemGridRes
import org.example.erp.features.inventory.domain.entity.InventoryDestination
import org.example.erp.features.inventory.presentation.category.CategoryRoute
import org.example.erp.features.inventory.presentation.product.ProductRoute
import org.example.erp.features.inventory.presentation.unitOfMeasures.UnitOfMeasuresRoute
import org.example.erp.features.inventory.presentation.warehouses.WarehouseRoute
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun InventoryRoute(
    viewModel: InventoryViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    InventoryScreen(state = state, onEvent = viewModel::handleEvent)
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun InventoryScreen(
    state: InventoryState,
    onEvent: (InventoryEvent) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<InventoryDestination>()
    ListDetailPaneScaffold(
        directive = scaffoldNavigator.scaffoldDirective,
        value = scaffoldNavigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                ItemGridRes(
                    list = InventoryDestination.getAll(),
                    onItemClick = {
                        onEvent(InventoryEvent.UpdateSelectDestination(it))
                        scope.launch {
                            scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail, it)
                        }
                    },
                    labelProvider = {
                        when (it) {
                            InventoryDestination.UnitOfMeasures -> Res.string.unit_of_measures
                            InventoryDestination.Warehouses -> Res.string.warehouses
                            InventoryDestination.Categories -> Res.string.categories
                            InventoryDestination.Products -> Res.string.products
                        }
                    },
                    isSelected = { it == state.selectedDestination },
                    modifier = Modifier.padding(16.dp)
                )
            }
        },
        detailPane = {
            AnimatedPane {
                when (scaffoldNavigator.currentDestination?.contentKey) {
                    InventoryDestination.UnitOfMeasures -> {
                        UnitOfMeasuresRoute(
                            onBack = {
                                scope.launch {
                                    scaffoldNavigator.navigateBack()
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = .1f))
                        )
                    }

                    InventoryDestination.Warehouses -> {
                        WarehouseRoute(
                            onBack = {
                                scope.launch {
                                    scaffoldNavigator.navigateBack()
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = .1f))
                        )
                    }

                    InventoryDestination.Categories -> {
                        CategoryRoute(
                            onBack = {
                                scope.launch {
                                    scaffoldNavigator.navigateBack()
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = .1f))
                        )
                    }

                    InventoryDestination.Products -> {
                        ProductRoute(
                            onBack = {
                                scope.launch {
                                    scaffoldNavigator.navigateBack()
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = .1f))
                        )
                    }

                    else -> {}
                }
            }
        },
    )
}