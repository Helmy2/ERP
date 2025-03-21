package org.example.erp

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.erp.core.App
import org.example.erp.core.domain.navigation.Destination
import org.example.erp.core.presentation.AppTheme
import org.example.erp.di.initKoin
import org.example.erp.features.user.domain.usecase.IsUserLongedInUseCase
import org.jetbrains.compose.reload.DevelopmentEntryPoint
import org.koin.compose.koinInject

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "ERP",
        ) {
            DevelopmentEntryPoint {
                val isUserLongedInUseCase = koinInject<IsUserLongedInUseCase>()

                var startDestination by remember { mutableStateOf<Destination?>(null) }

                LaunchedEffect(Unit) {
                    startDestination =
                        if (isUserLongedInUseCase()) Destination.Main else Destination.Auth
                }
                AppTheme {
                    AnimatedContent(startDestination != null) {
                        if (it) {
                            App(startDestination!!)
                        } else {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}