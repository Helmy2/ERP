package org.example.erp.core.domain.usecase

import org.example.erp.core.domain.entity.ThemeMode
import org.example.erp.core.domain.repository.SettingsManager

class ChangeThemeModeUseCase(private val repo: SettingsManager) {
    suspend operator fun invoke(mode: ThemeMode) {
        repo.changeTheme(mode)
    }
}

