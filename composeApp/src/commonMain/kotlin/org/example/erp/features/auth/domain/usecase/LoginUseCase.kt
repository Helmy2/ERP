package org.example.erp.features.auth.domain.usecase

import org.example.erp.features.auth.domain.repository.AuthRepo

class LoginUseCase(private val repo: AuthRepo) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return repo.signInWithEmailAndPassword(email, password)
    }
}