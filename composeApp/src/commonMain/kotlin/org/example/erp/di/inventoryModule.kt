package org.example.erp.di

import kotlinx.coroutines.Dispatchers
import org.example.erp.features.inventory.data.repository.InventoryRepsImpl
import org.example.erp.features.inventory.domain.repository.InventoryReps
import org.example.erp.features.inventory.presentation.inventory.InventoryViewModel
import org.example.erp.features.inventory.presentation.unitOfMeasures.UnitOfMeasuresViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val inventoryModule = module {
    single<InventoryReps> {
        InventoryRepsImpl(
            supabaseClient = get(),
            dispatcher = Dispatchers.IO
        )
    }

    viewModel { InventoryViewModel(get()) }
    viewModel { UnitOfMeasuresViewModel(get(),get(),get()) }
}