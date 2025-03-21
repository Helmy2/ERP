package org.example.erp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.erp.core.App
import org.example.erp.di.initKoin
import org.jetbrains.compose.reload.DevelopmentEntryPoint

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "ERP",
        ) {
            DevelopmentEntryPoint {
                App()
            }
        }
    }
}