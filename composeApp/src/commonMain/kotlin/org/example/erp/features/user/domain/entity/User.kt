package org.example.erp.features.user.domain.entity

import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.serialization.json.jsonPrimitive
import org.example.erp.core.util.DISPLAY_NAME_KEY


data class User(
    val id: String,
    val name: String,
    val email: String,
)

fun UserInfo.toDomainUser(): User = User(
    id = this.id,
    name = userMetadata?.get(DISPLAY_NAME_KEY)?.jsonPrimitive?.content ?: "",
    email = this.email ?: "",
)