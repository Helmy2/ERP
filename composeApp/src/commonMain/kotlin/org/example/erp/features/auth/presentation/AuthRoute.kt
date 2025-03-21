package org.example.erp.features.auth.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.example.erp.features.auth.presentation.login.LoginScreen
import org.example.erp.features.auth.presentation.login.LoginViewModel
import org.example.erp.features.auth.presentation.register.RegisterScreen
import org.example.erp.features.auth.presentation.register.RegisterViewModel
import org.example.erp.core.domain.navigation.Destination
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.authRoute() {
    composable<Destination.Auth.Login> {
        val viewModel: LoginViewModel = koinViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        LoginScreen(
            state = state,
            onEvent = viewModel::handleEvent,
            modifier = Modifier.fillMaxSize().systemBarsPadding()
        )
    }
    composable<Destination.Auth.Register> {
        val viewModel: RegisterViewModel = koinViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        RegisterScreen(
            state = state,
            onEvent = viewModel::handleEvent,
            modifier = Modifier.fillMaxSize().systemBarsPadding()
        )
    }
}


