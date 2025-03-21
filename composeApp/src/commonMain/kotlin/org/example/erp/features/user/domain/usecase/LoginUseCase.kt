package org.example.erp.features.user.domain.usecase

import org.example.erp.features.user.domain.repository.UserRepo

class LoginUseCase(private val repo: UserRepo) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return repo.signInWithEmailAndPassword(email, password)
    }
}