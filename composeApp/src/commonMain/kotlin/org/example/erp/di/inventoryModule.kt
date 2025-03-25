package org.example.erp.di

import kotlinx.coroutines.Dispatchers
import org.example.erp.features.inventory.data.repository.InventoryRepsImpl
import org.example.erp.features.inventory.domain.repository.InventoryReps
import org.example.erp.features.inventory.presentation.InventoryViewModel
import org.example.erp.features.user.data.exception.AuthExceptionMapper
import org.example.erp.features.user.data.repository.UserRepoImpl
import org.example.erp.features.user.domain.repository.UserRepo
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val inventoryModule = module {


    single<InventoryReps> {
        InventoryRepsImpl(
            supabaseClient = get(),
            exceptionMapper = AuthExceptionMapper(),
            dispatcher = Dispatchers.IO
        )
    }

    viewModel { InventoryViewModel(get()) }
}