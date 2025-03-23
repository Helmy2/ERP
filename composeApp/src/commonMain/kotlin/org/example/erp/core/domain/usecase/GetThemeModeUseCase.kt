package org.example.erp.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.example.erp.core.domain.entity.ThemeMode
import org.example.erp.core.domain.repository.SettingsManager

class GetThemeModeUseCase(private val repo: SettingsManager) {
    operator fun invoke(): Flow<ThemeMode> {
        return repo.getThemeMode()
    }
}

