package org.example.erp.core.domain.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.dashboard
import erp.composeapp.generated.resources.inventory
import erp.composeapp.generated.resources.settings
import org.jetbrains.compose.resources.StringResource


data class TopLevelRoute(val name: StringResource, val route: Destination, val icon: ImageVector)

object TopLevelRoutes {
    val routes = listOf(
        TopLevelRoute(Res.string.dashboard, Destination.Main.Dashboard, Icons.Default.Dashboard),
        TopLevelRoute(Res.string.inventory, Destination.Main.Inventory, Icons.Default.Inventory),
        TopLevelRoute(Res.string.settings, Destination.Main.Settings, Icons.Default.Settings),
    )
}