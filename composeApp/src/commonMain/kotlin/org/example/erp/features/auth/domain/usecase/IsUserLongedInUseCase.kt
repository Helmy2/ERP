package org.example.erp.features.auth.domain.usecase

import kotlinx.coroutines.flow.first
import org.example.erp.features.auth.domain.repository.AuthRepo

class IsUserLongedInUseCase(private val repo: AuthRepo) {
    suspend operator fun invoke(): Boolean {
        return repo.isUserLongedIn().first()
    }
}