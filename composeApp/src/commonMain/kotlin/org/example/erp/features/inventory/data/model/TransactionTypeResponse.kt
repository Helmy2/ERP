package org.example.erp.features.inventory.data.model

import kotlinx.serialization.Serializable

@Serializable
enum class TransactionTypeResponse {
    ADDITION,
    SUBTRACTION,
}