package org.example.erp.features.auth.data.repository

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.example.erp.core.domain.exceptions.ExceptionMapper
import org.example.erp.core.util.DISPLAY_NAME_KEY
import org.example.erp.features.auth.domain.repository.AuthRepo

class AuthRepoImpl(
    private val auth: Auth,
    private val exceptionMapper: ExceptionMapper,
    private val dispatcher: CoroutineDispatcher,
) : AuthRepo {

    override fun isUserLongedIn(): Flow<Boolean> {
        return channelFlow {
            auth.sessionStatus.collectLatest {
                when (it) {
                    is SessionStatus.Authenticated -> trySend(true)
                    is SessionStatus.NotAuthenticated -> trySend(false)
                    else -> {}
                }
            }
        }
    }

    override suspend fun signInWithEmailAndPassword(
        email: String, password: String
    ): Result<Unit> = withContext(dispatcher) {
        try {
            auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(exceptionMapper.map(e))
        }
    }

    override suspend fun createUserWithEmailAndPassword(
        name: String, email: String, password: String
    ): Result<Unit> = withContext(dispatcher) {
        try {
            auth.signUpWith(Email) {
                this.email = email
                this.password = password

                data = buildJsonObject {
                    put(DISPLAY_NAME_KEY, name)
                }
                this.data
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(exceptionMapper.map(e))
        }
    }
}
