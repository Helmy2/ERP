package org.example.erp.features.user.domain.usecase

import org.example.erp.features.user.domain.repository.UserRepo

class GetDisplayNameUseCase(
    private val userRepo: UserRepo
) {
    suspend operator fun invoke(id: String?): String {
        return userRepo.getDisplayName(id)
    }
}