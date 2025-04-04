package org.example.erp.features.user.domain.usecase

import org.example.erp.features.user.domain.repository.UserRepo

class IsUserLongedInFlowUseCase(private val repo: UserRepo) {
    operator fun invoke() = repo.isUserLongedIn()
}