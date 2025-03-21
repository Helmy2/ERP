package org.example.erp.features.auth.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepo {
    fun isUserLongedIn(): Flow<Boolean>

    suspend fun signInWithEmailAndPassword(
        email: String, password: String
    ): Result<Unit>

    suspend fun createUserWithEmailAndPassword(
        name: String,
        email: String,
        password: String
    ): Result<Unit>
}