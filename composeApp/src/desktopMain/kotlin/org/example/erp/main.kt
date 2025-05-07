package org.example.erp

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.something_went_wrong
import org.example.erp.core.App
import org.example.erp.core.domain.navigation.Destination
import org.example.erp.core.presentation.AppTheme
import org.example.erp.di.initKoin
import org.example.erp.features.user.domain.usecase.IsUserLongedInUseCase
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "ERP",
            state = rememberWindowState(width = 1000.dp, height = 600.dp)
        ) {
            val isUserLongedInUseCase = koinInject<IsUserLongedInUseCase>()

            var entryDestination by remember { mutableStateOf<Result<Destination>?>(null) }

            LaunchedEffect(Unit) {
                isUserLongedInUseCase().also {
                    entryDestination =
                        it.map { flag -> if (flag) Destination.Main else Destination.Auth }
                }
            }
            AppTheme {
                AnimatedContent(entryDestination != null) {
                    if (it) {
                        entryDestination!!.onSuccess { destination ->
                            App(destination)
                        }.onFailure {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text(stringResource(Res.string.something_went_wrong))
                            }
                        }
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