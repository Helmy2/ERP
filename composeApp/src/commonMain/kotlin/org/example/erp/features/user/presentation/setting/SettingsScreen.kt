package org.example.erp.features.user.presentation.setting

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.email
import erp.composeapp.generated.resources.logout
import erp.composeapp.generated.resources.name
import erp.composeapp.generated.resources.theme
import org.example.erp.features.user.presentation.components.ClickableText
import org.example.erp.features.user.presentation.components.ThemeSwitch
import org.example.erp.features.user.presentation.components.UpdateNameDialog
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsRoute(
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    SettingsScreen(state = state, onEvent = viewModel::handleEvent)
}

@Composable
fun SettingsScreen(
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.sizeIn(maxWidth = 400.dp)
                    .padding(vertical =  8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(Res.string.email),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = state.user?.email ?: "Unknown",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.sizeIn(maxWidth = 400.dp).fillMaxWidth()
            ) {
                Text(
                    text = stringResource(Res.string.name),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.weight(1f))
                ClickableText(
                    content = {
                        Row {
                            Text(text = state.user?.name ?: "Anonymous")
                            Spacer(Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = "Edit Name"
                            )
                        }
                    },
                    onClick = { onEvent(SettingsEvent.EditeNameDialog(true)) }
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.sizeIn(maxWidth = 400.dp).fillMaxWidth()
            ) {
                Text(
                    text = stringResource(Res.string.theme),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.weight(1f))
                ThemeSwitch(
                    themeMode = state.themeMode,
                    onThemeChange = { onEvent(SettingsEvent.ThemeChanged(it)) }
                )
            }
            Button(
                onClick = { onEvent(SettingsEvent.Logout) },
            ) {
                Text(text = stringResource(Res.string.logout))
            }
        }
        AnimatedVisibility(state.showEditNameDialog) {
            UpdateNameDialog(
                name = state.name,
                onValueChange = { onEvent(SettingsEvent.UpdateName(it)) },
                onConfirm = { onEvent(SettingsEvent.ConfirmUpdateName) },
                onDismiss = { onEvent(SettingsEvent.EditeNameDialog(false)) },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
