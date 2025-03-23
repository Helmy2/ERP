package org.example.erp.features.user.presentation.setting

import org.example.erp.core.domain.entity.Language
import org.example.erp.core.domain.entity.ThemeMode
import org.example.erp.features.user.domain.entity.User

data class SettingsState(
    val user: User? = null,
    val showEditNameDialog: Boolean = false,
    val showEditProfilePictureDialog: Boolean = false,
    val showThemeDialog: Boolean = false,
    val showLanguageDialog: Boolean = false,
    val name: String = "",
    val profilePictureLoading: Boolean = false,
    val themeMode: ThemeMode = ThemeMode.System,
    val language: Language = Language.English,
)