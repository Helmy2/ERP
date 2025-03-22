package org.example.erp.features.user.presentation.setting

import org.example.erp.core.domain.entity.ThemeMode

sealed interface SettingsEvent {
    data object Logout : SettingsEvent
    data class EditeNameDialog(val show: Boolean) : SettingsEvent
    data class UpdateName(val name: String) : SettingsEvent
    data class ThemeChanged(val mode: ThemeMode): SettingsEvent
    data object ConfirmUpdateName : SettingsEvent
}