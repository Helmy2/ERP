package org.example.erp.features.inventory.data.model

import androidx.room.Entity
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.erp.core.util.SupabaseConfig

@Entity(primaryKeys = ["id"])
@Serializable
data class ProductResponse(
    @SerialName(SupabaseConfig.ID_ROW)
    val id: String,
    @SerialName(SupabaseConfig.CODE_ROW)
    val code: String,
    @SerialName(SupabaseConfig.NAME_ROW)
    val name: String,
    @SerialName(SupabaseConfig.SKU_ROW)
    val sku: String? = null,
    @SerialName(SupabaseConfig.DESCRIPTION_ROW)
    val description: String? = null,
    @SerialName(SupabaseConfig.CATEGORY_ID_ROW)
    val categoryId: String? = null,
    @SerialName(SupabaseConfig.UNIT_PRICE_ROW)
    val unitPrice: Double? = null,
    @SerialName(SupabaseConfig.COST_PRICE_ROW)
    val costPrice: Double? = null,
    @SerialName(SupabaseConfig.UNIT_OF_MEASURE_ID_ROW)
    val unitOfMeasureId: String,
    @SerialName(SupabaseConfig.DELETED_AT_ROW)
    val deletedAt: Instant? = null,
    @SerialName(SupabaseConfig.CREATED_AT_ROW)
    val createdAt: Instant? = null,
    @SerialName(SupabaseConfig.UPDATED_AT_ROW)
    val updatedAt: Instant? = null,
    @SerialName(SupabaseConfig.CREATED_BY_ROW)
    val createdBy: String? = null,
    @SerialName(SupabaseConfig.UPDATED_BY_ROW)
    val updatedBy: String? = null
)