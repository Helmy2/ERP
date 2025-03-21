package org.example.erp.core.domain.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.ui.graphics.vector.ImageVector


data class TopLevelRoute(val name: String, val route: Destination, val icon: ImageVector)

object TopLevelRoutes {
    val routes = listOf(
        TopLevelRoute("Dashboard", Destination.Main.Dashboard, Icons.Default.Dashboard),
        TopLevelRoute("Inventory", Destination.Main.Inventory, Icons.Default.Inventory),
    )
}