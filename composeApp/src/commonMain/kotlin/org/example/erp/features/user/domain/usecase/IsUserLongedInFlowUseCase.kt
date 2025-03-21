package org.example.erp.features.user.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.example.erp.features.user.domain.repository.UserRepo

class IsUserLongedInFlowUseCase(private val repo: UserRepo) {
    operator fun invoke(): Flow<Boolean> {
        return repo.isUserLongedIn()
    }
}