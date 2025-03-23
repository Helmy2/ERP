package org.example.erp.core.domain.usecase

import org.example.erp.core.domain.repository.SettingsManager

class GetLanguageUseCase(private val repo: SettingsManager) {
    operator fun invoke() = repo.getLanguage()
}