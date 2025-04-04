package org.example.erp.core.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.back_button
import org.jetbrains.compose.resources.stringResource

@Composable
fun BackButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    val layoutDirection = LocalLayoutDirection.current

    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            when (layoutDirection) {
                LayoutDirection.Ltr -> Icons.Default.ChevronLeft
                LayoutDirection.Rtl -> Icons.Default.ChevronRight
            },
            contentDescription = stringResource(Res.string.back_button)
        )
    }
}