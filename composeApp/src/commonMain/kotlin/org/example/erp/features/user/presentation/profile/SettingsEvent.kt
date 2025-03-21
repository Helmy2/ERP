package org.example.erp.features.user.presentation.profile

sealed interface SettingsEvent {
    data object Logout : SettingsEvent
    data class EditeNameDialog(val show: Boolean) : SettingsEvent
    data class UpdateName(val name: String) : SettingsEvent
    data object ConfirmUpdateName : SettingsEvent
}