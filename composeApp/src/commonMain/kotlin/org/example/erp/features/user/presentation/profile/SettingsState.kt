package org.example.erp.features.user.presentation.profile

import org.example.erp.features.user.domain.entity.User

data class SettingsState(
    val user: User? = null,
    val showEditNameDialog: Boolean = false,
    val showEditProfilePictureDialog: Boolean = false,
    val name: String = "",
    val profilePictureLoading: Boolean = false
)