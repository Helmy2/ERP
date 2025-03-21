package org.example.erp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.compose.reload.DevelopmentEntryPoint

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ERP",
    ) {
        DevelopmentEntryPoint {
            App()
        }
    }
}