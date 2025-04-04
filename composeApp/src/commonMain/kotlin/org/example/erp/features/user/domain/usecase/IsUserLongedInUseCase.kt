package org.example.erp.features.user.domain.usecase

import kotlinx.coroutines.flow.first
import org.example.erp.features.user.domain.repository.UserRepo

class IsUserLongedInUseCase(private val repo: UserRepo) {
    suspend operator fun invoke() = repo.isUserLongedIn().first()
}