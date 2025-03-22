package org.example.erp.core.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.erp.core.domain.entity.ThemeMode

interface ThemeModeSource {
    fun getThemeMode(): Flow<ThemeMode>
    suspend fun changeTheme(mode: ThemeMode)
}