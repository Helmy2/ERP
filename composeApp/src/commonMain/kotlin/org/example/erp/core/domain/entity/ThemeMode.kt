package org.example.erp.core.domain.entity

import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.dark_mode
import erp.composeapp.generated.resources.light_mode
import erp.composeapp.generated.resources.system_default
import org.jetbrains.compose.resources.StringResource

enum class ThemeMode {
    System,
    Light,
    Dark;

    fun resource(): StringResource {
        return when (this) {
            System -> Res.string.system_default
            Light -> Res.string.light_mode
            Dark -> Res.string.dark_mode
        }
    }
}



