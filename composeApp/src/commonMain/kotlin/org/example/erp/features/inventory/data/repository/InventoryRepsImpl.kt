package org.example.erp.features.inventory.data.repository

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.example.erp.core.util.DISPLAY_NAME_KEY
import org.example.erp.core.util.SupabaseConfig
import org.example.erp.core.util.SupabaseConfig.USER_ROLE
import org.example.erp.features.inventory.data.exception.UnitOfMeasureNotFoundException
import org.example.erp.features.inventory.data.model.UnitsOfMeasureResponse
import org.example.erp.features.inventory.domain.entity.UnitsOfMeasure
import org.example.erp.features.inventory.domain.mapper.toDomain
import org.example.erp.features.inventory.domain.repository.InventoryReps
import org.example.erp.features.user.domain.entity.User

@OptIn(SupabaseExperimental::class)
class InventoryRepsImpl(
    private val supabaseClient: SupabaseClient,
    private val dispatcher: CoroutineDispatcher
) : InventoryReps {

    override fun getAllUnitsOfMeasure(): Flow<List<UnitsOfMeasure>> =
        supabaseClient
            .from(SupabaseConfig.UNIT_OF_MEASURE)
            .selectAsFlow(UnitsOfMeasureResponse::id)
            .map { list ->
                list.map {
                    it.toDomain(
                        createdBy = getUserById(it.createdBy!!),
                        updatedBy = it.updatedBy?.let { userId -> getUserById(userId) }
                    )
                }
            }
            .flowOn(dispatcher)

    private suspend fun getUserById(id: String): String {
        val name = supabaseClient.from(USER_ROLE)
            .select {
                filter { User::id eq id }
            }.decodeSingle<Map<String, String?>>()

        return name[DISPLAY_NAME_KEY] ?: ""
    }

    override suspend fun getUnitOfMeasureByCode(code: String): Result<UnitsOfMeasure> =
        withContext(dispatcher) {
            runCatching {
                val result = supabaseClient
                    .from(SupabaseConfig.UNIT_OF_MEASURE)
                    .select { filter { UnitsOfMeasureResponse::code eq code } }
                    .decodeSingleOrNull<UnitsOfMeasureResponse>()

                val unit = result?.toDomain(
                    createdBy = getUserById(result.createdBy!!),
                    updatedBy = result.updatedBy?.let { userId -> getUserById(userId) }
                )

                unit ?: throw UnitOfMeasureNotFoundException()
            }
        }

    override suspend fun createUnitOfMeasure(
        code: String,
        name: String,
        description: String
    ): Result<Unit> =
        withContext(dispatcher) {
            runCatching {
                supabaseClient
                    .from(SupabaseConfig.UNIT_OF_MEASURE)
                    .insert(
                        UnitsOfMeasureResponse(
                            code = code,
                            name = name,
                            description = description
                        )
                    )
                Unit
            }
        }

    override suspend fun updateUnitOfMeasure(
        id: String,
        code: String,
        name: String,
        description: String
    ): Result<Unit> =
        withContext(dispatcher) {
            runCatching {
                supabaseClient
                    .from(SupabaseConfig.UNIT_OF_MEASURE)
                    .update(
                        UnitsOfMeasureResponse(
                            code = code,
                            name = name,
                            description = description
                        )
                    ) {
                        filter {
                            UnitsOfMeasureResponse::id eq id
                        }
                    }
                Unit
            }
        }

    override suspend fun deleteUnitOfMeasure(code: String): Result<Unit> = withContext(dispatcher) {
        runCatching {
            supabaseClient
                .from(SupabaseConfig.UNIT_OF_MEASURE)
                .delete { filter { UnitsOfMeasureResponse::code eq code } }
            Unit
        }
    }
}
