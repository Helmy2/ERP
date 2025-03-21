package org.example.erp.features.auth.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.email
import erp.composeapp.generated.resources.login
import erp.composeapp.generated.resources.login_to_your_account
import erp.composeapp.generated.resources.no_account
import erp.composeapp.generated.resources.welcome_back
import org.example.erp.core.presentation.components.AdaptivePane
import org.example.erp.features.auth.presentation.components.AuthButton
import org.example.erp.features.auth.presentation.components.AuthHeader
import org.example.erp.features.auth.presentation.components.AuthPasswordField
import org.example.erp.features.auth.presentation.components.AuthTextButton
import org.example.erp.features.auth.presentation.components.AuthTextField
import org.jetbrains.compose.resources.stringResource


@Composable
fun LoginScreen(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focus = LocalFocusManager.current
    AdaptivePane(
        modifier.verticalScroll(rememberScrollState()).padding(16.dp),
        firstPane = {
            AuthHeader(
                title = Res.string.welcome_back,
                body = Res.string.login_to_your_account,
                modifier = Modifier.size(300.dp).padding(16.dp),
            )
        },
        secondPane = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.sizeIn(maxWidth = 600.dp)
            ) {
                AuthTextField(
                    value = state.email,
                    label = stringResource(Res.string.email),
                    error = state.emailError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
                    ),
                    onValueChange = { onEvent(LoginEvent.EmailChanged(it)) },
                    modifier = Modifier.fillMaxWidth()
                )

                AuthPasswordField(
                    value = state.password,
                    error = state.passwordError,
                    isVisible = state.isPasswordVisible,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                    ),
                    onValueChange = { onEvent(LoginEvent.PasswordChanged(it)) },
                    onVisibilityToggle = { onEvent(LoginEvent.TogglePasswordVisibility) },
                    onDone = {
                        focus.clearFocus()
                        onEvent(LoginEvent.Login)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    supportingText = if (state.passwordError != null) {
                        {
                            Text(
                                text = stringResource(state.passwordError),
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.labelSmall,
                            )
                        }
                    } else {
                        null
                    },
                )

                AuthButton(
                    isLoading = state.loading,
                    text = stringResource(Res.string.login),
                    onClick = {
                        focus.clearFocus()
                        onEvent(LoginEvent.Login)
                    },
                    modifier = Modifier.fillMaxWidth(),
                )

                AuthTextButton(
                    onClick = { onEvent(LoginEvent.NavigateToRegister) },
                    content = {
                        Text(
                            stringResource(Res.string.no_account),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                )
            }
        },
    )
}

