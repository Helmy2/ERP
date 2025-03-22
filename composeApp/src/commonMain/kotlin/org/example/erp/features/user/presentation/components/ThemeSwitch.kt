package org.example.erp.features.user.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.ic_dark_mode
import erp.composeapp.generated.resources.ic_light_mode
import org.example.erp.core.domain.entity.ThemeMode
import org.example.erp.core.presentation.local.LocalThemeIsDark
import org.jetbrains.compose.resources.vectorResource

@Composable
fun ThemeSwitch(
    modifier: Modifier = Modifier,
    onThemeChange: (ThemeMode) -> Unit,
    themeMode: ThemeMode,
) {
    var openMenu by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ClickableText(
            content = {
                Row {
                    Text(themeMode.name)
                    Spacer(Modifier.width(8.dp))
                    Icon(Icons.Outlined.Edit, contentDescription = null)
                }
            },
            onClick = {
                openMenu = true
            }
        )
        DropdownMenu(
            expanded = openMenu,
            onDismissRequest = { openMenu = false },
            modifier = Modifier,
            content = {
                ThemeMode.entries.forEach { themeMode ->
                    DropdownMenuItem(
                        text = {
                            Text(themeMode.name)
                        },
                        onClick = {
                            onThemeChange(themeMode)
                        }
                    )
                }
            }
        )
    }
}