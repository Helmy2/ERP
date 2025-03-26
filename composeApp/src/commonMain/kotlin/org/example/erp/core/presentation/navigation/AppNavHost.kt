package org.example.erp.core.presentation.navigation

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.example.erp.core.domain.navigation.Destination
import org.example.erp.core.domain.navigation.Navigator
import org.example.erp.features.inventory.presentation.inventory.InventoryRoute
import org.example.erp.features.inventory.presentation.unitOfMeasures.UnitOfMeasuresRoute
import org.example.erp.features.user.presentation.login.LoginRoute
import org.example.erp.features.user.presentation.setting.SettingsRoute
import org.example.erp.features.user.presentation.register.RegisterRoute
import org.koin.compose.koinInject

@Composable
fun AppNavHost(
    startDestination: Destination,
    modifier: Modifier = Modifier,
) {
    val navigator = koinInject<Navigator>()
    NavHost(
        navController = navigator.navController,
        startDestination = startDestination,
        modifier = modifier.systemBarsPadding(),
    ) {
        navigation<Destination.Main>(
            startDestination = Destination.Main.Dashboard
        ) {
            composable<Destination.Main.Dashboard> {
                Text("Dashboard")
            }
            composable<Destination.Main.Inventory> {
                InventoryRoute()
            }
            composable<Destination.Main.Settings> {
                SettingsRoute()
            }
        }

        navigation<Destination.Auth>(
            startDestination = Destination.Auth.Login
        ) {
            composable<Destination.Auth.Login> {
                LoginRoute()
            }
            composable<Destination.Auth.Register> {
                RegisterRoute()
            }
        }

        composable<Destination.UnitOfMeasures> {
            UnitOfMeasuresRoute()
        }
    }
}

