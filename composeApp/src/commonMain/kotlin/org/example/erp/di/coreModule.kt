package org.example.erp.di

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavHostController
import org.example.erp.core.domain.repository.SettingsManager
import org.example.erp.core.data.repository.SettingsManagerImpl
import org.example.erp.core.domain.navigation.Navigator
import org.example.erp.core.domain.snackbar.SnackbarManager
import org.example.erp.core.domain.usecase.ChangeThemeModeUseCase
import org.example.erp.core.domain.usecase.GetThemeModeUseCase
import org.example.erp.core.presentation.navigation.NavigatorImpl
import org.example.erp.core.presentation.snackbar.SnackbarManagerImpl
import org.koin.dsl.module

val coreModule = module {
    single<Navigator> { (navController: NavHostController) ->
        NavigatorImpl(navController)
    }
    single<SnackbarManager> { (snackbarHostState: SnackbarHostState) ->
        SnackbarManagerImpl(snackbarHostState)
    }
    single<SettingsManager> {
        SettingsManagerImpl(get())
    }
    factory { GetThemeModeUseCase(get()) }
    single { ChangeThemeModeUseCase(get()) }
}