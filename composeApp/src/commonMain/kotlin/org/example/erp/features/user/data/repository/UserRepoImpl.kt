package org.example.erp.features.user.data.repository

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.example.erp.core.domain.exceptions.ExceptionMapper
import org.example.erp.core.util.DISPLAY_NAME_KEY
import org.example.erp.features.user.domain.entity.User
import org.example.erp.features.user.domain.entity.toDomainUser
import org.example.erp.features.user.domain.repository.UserRepo

class UserRepoImpl(
    private val supabaseClient: SupabaseClient,
    private val exceptionMapper: ExceptionMapper,
    private val dispatcher: CoroutineDispatcher,
) : UserRepo {


    override val currentUser: Flow<Result<User?>> = supabaseClient.auth.sessionStatus.map {
        try {
            val user = supabaseClient.auth.currentUserOrNull()?.toDomainUser()
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(exceptionMapper.map(e))
        }
    }.catch { e ->
        emit(Result.failure(exceptionMapper.map(e)))
    }


    override suspend fun signOut(): Result<Unit> = withContext(dispatcher) {
        try {
            supabaseClient.auth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(exceptionMapper.map(e))
        }
    }

    override suspend fun updateDisplayName(name: String): Result<Unit> = withContext(dispatcher) {
        try {
            supabaseClient.auth.updateUser {
                data {
                    put(DISPLAY_NAME_KEY, name)
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(exceptionMapper.map(e))
        }
    }

    override fun isUserLongedIn(): Flow<Boolean> {
        return channelFlow {
            supabaseClient.auth.sessionStatus.collectLatest {
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
            supabaseClient.auth.signInWith(Email) {
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
            supabaseClient.auth.signUpWith(Email) {
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
