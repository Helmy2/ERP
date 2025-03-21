package org.example.erp.features.user.domain.usecase

import org.example.erp.features.user.domain.repository.UserRepo

class LogoutUseCase(private val repo: UserRepo) {
    suspend operator fun invoke(): Result<Unit> {
        return repo.signOut()
    }
}