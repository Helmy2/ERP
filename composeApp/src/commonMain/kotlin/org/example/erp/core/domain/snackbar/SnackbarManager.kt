package org.example.erp.core.domain.snackbar

import androidx.compose.material3.SnackbarHostState

interface SnackbarManager {
    val snackbarHostState: SnackbarHostState
    suspend fun showErrorSnackbar(value: String)
    suspend fun showSnackbar(value: String)
    suspend fun dismissSnackbar()
}