package org.example.erp.core.util


import erp.composeapp.generated.resources.Res
import erp.composeapp.generated.resources.password_requirement_length
import erp.composeapp.generated.resources.password_requirement_special
import erp.composeapp.generated.resources.password_requirement_uppercase
import org.example.erp.features.auth.domain.entity.Requirement

fun calculatePasswordRequirements(password: String): List<Requirement> {
    return listOf(
        Requirement(Res.string.password_requirement_length, password.length >= 8),
        Requirement(
            Res.string.password_requirement_special,
            password.any { !it.isLetterOrDigit() }),
        Requirement(
            Res.string.password_requirement_uppercase,
            password.any { it.isUpperCase() })
    )
}