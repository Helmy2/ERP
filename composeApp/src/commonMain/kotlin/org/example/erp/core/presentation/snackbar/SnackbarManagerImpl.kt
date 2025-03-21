package org.example.erp.core.presentation.snackbar

import androidx.compose.material3.SnackbarHostState
import org.example.erp.core.domain.snackbar.SnackbarManager


class SnackbarManagerImpl(
    override val snackbarHostState: SnackbarHostState
) : SnackbarManager {

    override suspend fun showErrorSnackbar(value: String) {
        println("SnackbarManagerImpl.showErrorSnackbar: $value")
        dismissSnackbar()
        snackbarHostState.showSnackbar(value)
    }

    override suspend fun showSnackbar(value: String) {
        dismissSnackbar()
        snackbarHostState.showSnackbar(value)
    }

    override suspend fun dismissSnackbar() {
        snackbarHostState.currentSnackbarData?.dismiss()
    }
}