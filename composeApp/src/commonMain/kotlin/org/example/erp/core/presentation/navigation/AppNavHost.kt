package org.example.erp.core.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.example.erp.core.domain.navigation.Destination
import org.example.erp.core.domain.navigation.Navigator
import org.example.erp.features.auth.presentation.authRoute
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
        modifier = modifier,
    ) {
        navigation<Destination.Main>(
            startDestination = Destination.Main.Dashboard
        ) {
            composable<Destination.Main.Dashboard> {
                Text("Dashboard")
            }
            composable<Destination.Main.Inventory> {
                Text("Inventory")
            }
        }

        navigation<Destination.Auth>(
            startDestination = Destination.Auth.Login
        ) {
            authRoute()
        }
    }
}

