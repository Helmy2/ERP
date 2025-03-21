package org.example.erp.features.auth.presentation.register

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import erp.composeapp.generated.resources.already_have_account
import erp.composeapp.generated.resources.create_account
import erp.composeapp.generated.resources.email
import erp.composeapp.generated.resources.name
import erp.composeapp.generated.resources.no_account
import erp.composeapp.generated.resources.register
import org.example.erp.features.auth.domain.entity.PasswordStrength
import org.example.erp.features.auth.presentation.components.AuthButton
import org.example.erp.features.auth.presentation.components.AuthHeader
import org.example.erp.features.auth.presentation.components.AuthPasswordField
import org.example.erp.features.auth.presentation.components.AuthTextButton
import org.example.erp.features.auth.presentation.components.AuthTextField
import org.example.erp.features.auth.presentation.components.PasswordStrengthIndicator
import org.jetbrains.compose.resources.stringResource

@Composable
fun RegisterScreen(
    state: RegisterState, onEvent: (RegisterEvent) -> Unit, modifier: Modifier = Modifier
) {
    val focus = LocalFocusManager.current

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.verticalScroll(rememberScrollState())
            .padding(16.dp),
    ) {

        AuthHeader(
            title = Res.string.create_account,
            body = Res.string.no_account,
        )

        AuthTextField(
            value = state.name,
            label = stringResource(Res.string.name),
            error = state.nameError,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            onValueChange = { onEvent(RegisterEvent.NameChanged(it)) },
            modifier = Modifier.sizeIn(maxWidth = 600.dp).fillMaxWidth()
        )

        AuthTextField(
            value = state.email,
            label = stringResource(Res.string.email),
            error = state.emailError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
            ),
            onValueChange = { onEvent(RegisterEvent.EmailChanged(it)) },
            modifier = Modifier.sizeIn(maxWidth = 600.dp).fillMaxWidth()
        )

        AuthPasswordField(
            value = state.password,
            error = state.passwordError,
            isVisible = state.isPasswordVisible,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
            ),
            onValueChange = { onEvent(RegisterEvent.PasswordChanged(it)) },
            onVisibilityToggle = { onEvent(RegisterEvent.TogglePasswordVisibility) },
            onDone = {
                focus.clearFocus()
                onEvent(RegisterEvent.Register)
            },
            modifier = Modifier.sizeIn(maxWidth = 600.dp).fillMaxWidth(),
            supportingText = {
                AnimatedContent(state.passwordError != null) {
                    if (it) {
                        Text(
                            text = if (state.passwordError != null) stringResource(state.passwordError) else "",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelSmall,
                        )
                    } else {
                        AnimatedVisibility(
                            state.passwordStrength != PasswordStrength.STRONG && state.password.isNotEmpty()
                        ) {
                            PasswordStrengthIndicator(
                                strength = state.passwordStrength,
                                requirements = state.passwordRequirements,
                            )
                        }
                    }
                }
            })

        AuthButton(
            isLoading = state.isLoading,
            text = stringResource(Res.string.register),
            onClick = {
                focus.clearFocus()
                onEvent(RegisterEvent.Register)
            },
            modifier = Modifier.sizeIn(maxWidth = 600.dp).fillMaxWidth()
        )


        AuthTextButton(
            onClick = { onEvent(RegisterEvent.NavigateToLogin) },
            content = {
                Text(
                    stringResource(Res.string.already_have_account),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
        )
    }
}