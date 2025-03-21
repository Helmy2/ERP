package org.example.erp.core.presentation.local

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

val LocalThemeIsDark = compositionLocalOf { mutableStateOf(true) }
