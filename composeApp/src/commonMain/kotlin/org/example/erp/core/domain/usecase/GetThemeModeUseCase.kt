package org.example.erp.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.example.erp.core.domain.entity.ThemeMode
import org.example.erp.core.domain.repository.ThemeModeSource

class GetThemeModeUseCase(private val repo: ThemeModeSource) {
    operator fun invoke(): Flow<ThemeMode> {
        return repo.getThemeMode()
    }
}

