package org.example.erp.core.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.erp.core.domain.entity.ThemeMode
import org.example.erp.core.domain.usecase.GetThemeModeUseCase
import org.koin.compose.koinInject

val LightColorScheme: ColorScheme = lightColorScheme()
val DarkColorScheme: ColorScheme = darkColorScheme()


val Shapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(16.dp)
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    val isDark = isSystemInDarkTheme()
    val getThemeModeUseCase: GetThemeModeUseCase = koinInject()
    val mode =
        getThemeModeUseCase().collectAsStateWithLifecycle(ThemeMode.System).value

    val systemIsDark = remember(mode) {
        when (mode) {
            ThemeMode.Light -> false
            ThemeMode.Dark -> true
            ThemeMode.System -> isDark
        }
    }

    SystemAppearance(!systemIsDark)
    MaterialTheme(
        colorScheme = getDynamicColorScheme(
            systemIsDark,
            DarkColorScheme,
            LightColorScheme,
        ),
        shapes = Shapes,
        content = { Surface(content = content) },
    )
}

@Composable
expect fun getDynamicColorScheme(
    darkTheme: Boolean,
    darkColorScheme: ColorScheme,
    lightColorScheme: ColorScheme,
): ColorScheme

@Composable
expect fun SystemAppearance(isDark: Boolean)

