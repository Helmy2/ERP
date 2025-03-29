package org.example.erp.features.inventory.presentation.inventory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.unit_of_measures
import erp.composeapp.generated.resources.warehouses
import org.example.erp.features.inventory.domain.entity.InventoryDestination
import org.example.erp.features.inventory.presentation.unitOfMeasures.UnitOfMeasuresRoute
import org.example.erp.features.inventory.presentation.warehouses.WarehouseRoute
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun InventoryRoute(
    viewModel: InventoryViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    InventoryScreen(state = state, onEvent = viewModel::handleEvent)
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalLayoutApi::class)
@Composable
fun InventoryScreen(
    state: InventoryState,
    onEvent: (InventoryEvent) -> Unit,
) {
    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<InventoryDestination>()
    ListDetailPaneScaffold(
        directive = scaffoldNavigator.scaffoldDirective,
        value = scaffoldNavigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                FlowRow {
                    Card(
                        onClick = {
                            scaffoldNavigator.navigateTo(
                                ListDetailPaneScaffoldRole.Detail,
                                InventoryDestination.UnitOfMeasures
                            )
                        },
                        modifier = Modifier.padding(16.dp),
                    ) {
                        Text(
                            stringResource(Res.string.unit_of_measures),
                            modifier = Modifier.padding(16.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                    Card(
                        onClick = {
                            scaffoldNavigator.navigateTo(
                                ListDetailPaneScaffoldRole.Detail,
                                InventoryDestination.Warehouses
                            )
                        },
                        modifier = Modifier.padding(16.dp),
                    ) {
                        Text(
                            stringResource(Res.string.warehouses),
                            modifier = Modifier.padding(16.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
            }
        },
        detailPane = {
            AnimatedPane {
                when (scaffoldNavigator.currentDestination?.content) {
                    InventoryDestination.UnitOfMeasures -> {
                        UnitOfMeasuresRoute(
                            onBack = {
                                scaffoldNavigator.navigateBack()
                            },
                            modifier = Modifier.fillMaxSize()
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = .1f))
                                .padding(16.dp)
                        )
                    }
                    InventoryDestination.Warehouses -> {
                        WarehouseRoute(
                            onBack = {
                                scaffoldNavigator.navigateBack()
                            },
                            modifier = Modifier.fillMaxSize()
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = .1f))
                                .padding(16.dp)
                        )
                    }
                    else -> {}
                }
            }
        }
    )
}