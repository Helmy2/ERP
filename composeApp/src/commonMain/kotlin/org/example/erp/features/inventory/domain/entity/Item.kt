package org.example.erp.features.inventory.domain.entity

import kotlinx.datetime.Instant

interface Item{
    val id: String
    val code: String
    val createdAt: Instant
    val updatedAt: Instant?
    val createdBy: String
    val updatedBy: String?
}
