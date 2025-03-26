package org.example.erp.features.user.domain.entity

import io.github.jan.supabase.auth.user.UserInfo


data class User(
    val id: String,
    val name: String,
    val email: String,
)

fun UserInfo.toDomainUser(
    name: String,
): User = User(
    id = this.id,
    name = name,
    email = this.email ?: "",
)