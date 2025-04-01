package org.example.erp.features.inventory.domain.entity

import kotlinx.datetime.Instant

data class Category(
    override  val id: String,
    override val code: String,
    val name: String,
    val children: List<Category>,
    val parentCategory: Category?,
    override val createdAt: Instant,
    override val updatedAt: Instant? = null,
    override val createdBy: String,
    override  val updatedBy: String? = null
):Item