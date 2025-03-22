package org.example.erp.core.domain.usecase

import org.example.erp.core.domain.entity.ThemeMode
import org.example.erp.core.domain.repository.ThemeModeSource

class ChangeThemeModeUseCase(private val repo: ThemeModeSource) {
    suspend operator fun invoke(mode: ThemeMode) {
        repo.changeTheme(mode)
    }
}