package org.example.erp.di

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavHostController
import org.example.erp.core.domain.navigation.Navigator
import org.example.erp.core.domain.snackbar.SnackbarManager
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
}