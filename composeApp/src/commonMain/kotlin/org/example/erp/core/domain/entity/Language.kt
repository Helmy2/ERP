package org.example.erp.core.domain.entity

import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.arabic
import erp.composeapp.generated.resources.english
import org.jetbrains.compose.resources.StringResource

enum class Language {
    English,
    Arabic;

    fun resource(): StringResource {
        return when (this) {
            English -> Res.string.english
            Arabic -> Res.string.arabic
        }
    }
}