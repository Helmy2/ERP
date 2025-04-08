package org.example.erp.features.inventory.domain.entity

import kotlinx.datetime.Instant

data class Category(
    override  val id: String,
    override val code: String,
    val name: String,
    val childrenIds: List<String>,
    val parentCategoryId: String?,
    override val createdAt: Instant,
    override val updatedAt: Instant? = null,
    override val createdBy: String,
    override  val updatedBy: String? = null
):Item