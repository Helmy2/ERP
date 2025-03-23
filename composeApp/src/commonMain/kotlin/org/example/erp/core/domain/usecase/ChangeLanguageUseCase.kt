package org.example.erp.core.domain.usecase

import org.example.erp.core.domain.entity.Language
import org.example.erp.core.domain.repository.SettingsManager

class ChangeLanguageUseCase(private val repo: SettingsManager) {
    suspend operator fun invoke(language: Language) {
        repo.changeLanguage(language)
    }
}