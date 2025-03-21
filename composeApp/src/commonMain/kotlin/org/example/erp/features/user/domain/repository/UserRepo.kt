package org.example.erp.features.user.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.erp.features.user.domain.entity.User

interface UserRepo {
    fun isUserLongedIn(): Flow<Boolean>

    suspend fun signInWithEmailAndPassword(
        email: String, password: String
    ): Result<Unit>

    suspend fun createUserWithEmailAndPassword(
        name: String,
        email: String,
        password: String
    ): Result<Unit>

    suspend fun signOut(): Result<Unit>
    val currentUser: Flow<Result<User?>>
    suspend fun updateDisplayName(name: String): Result<Unit>
}