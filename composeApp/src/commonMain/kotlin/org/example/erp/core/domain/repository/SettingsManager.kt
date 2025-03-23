package org.example.erp.core.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.erp.core.domain.entity.Language
import org.example.erp.core.domain.entity.ThemeMode

interface SettingsManager {
    fun getThemeMode(): Flow<ThemeMode>
    suspend fun changeTheme(mode: ThemeMode)
    suspend fun changeLanguage(language: Language)
    fun getLanguage(): Flow<Language>
}