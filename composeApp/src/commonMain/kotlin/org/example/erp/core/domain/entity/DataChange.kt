package org.example.erp.core.domain.entity

data class DataChange<T>(
    val toDelete: List<T>,
    val toInsert: List<T>,
)