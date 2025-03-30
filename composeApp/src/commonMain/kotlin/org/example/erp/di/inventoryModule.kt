package org.example.erp.di

import kotlinx.coroutines.Dispatchers
import org.example.erp.core.data.core.AppDatabase
import org.example.erp.features.inventory.data.local.dao.InventoryDao
import org.example.erp.features.inventory.data.local.dao.WarehouseDao
import org.example.erp.features.inventory.data.repository.InventoryRepsImpl
import org.example.erp.features.inventory.domain.repository.InventoryReps
import org.example.erp.features.inventory.domain.useCase.unitOfMeasures.CreateUnitOfMeasureUseCase
import org.example.erp.features.inventory.domain.useCase.warehouse.CreateWarehouseUseCase
import org.example.erp.features.inventory.domain.useCase.unitOfMeasures.DeleteUnitOfMeasureUseCase
import org.example.erp.features.inventory.domain.useCase.unitOfMeasures.GetAllUnitsOfMeasureUseCase
import org.example.erp.features.inventory.domain.useCase.unitOfMeasures.UpdateUnitOfMeasureUseCase
import org.example.erp.features.inventory.domain.useCase.warehouse.DeleteWarehouseUseCase
import org.example.erp.features.inventory.domain.useCase.warehouse.GetAllWarehouseUseCase
import org.example.erp.features.inventory.domain.useCase.warehouse.UpdateWarehouseUseCase
import org.example.erp.features.inventory.presentation.inventory.InventoryViewModel
import org.example.erp.features.inventory.presentation.unitOfMeasures.UnitOfMeasuresViewModel
import org.example.erp.features.inventory.presentation.warehouses.WarehouseViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val inventoryModule = module {
    single<InventoryReps> {
        InventoryRepsImpl(
            supabaseClient = get(),
            inventoryDao = get(),
            warehouseDao = get(),
            dispatcher = Dispatchers.IO,
        )
    }

    factory<InventoryDao> {
        get<AppDatabase>().inventoryDao()
    }
    factory<WarehouseDao> {
        get<AppDatabase>().warehouseDao()
    }

    factory { CreateUnitOfMeasureUseCase(get()) }
    factory { GetAllUnitsOfMeasureUseCase(get()) }
    factory { UpdateUnitOfMeasureUseCase(get()) }
    factory { DeleteUnitOfMeasureUseCase(get()) }

    factory { CreateWarehouseUseCase(get()) }
    factory { GetAllWarehouseUseCase(get()) }
    factory { UpdateWarehouseUseCase(get()) }
    factory { DeleteWarehouseUseCase(get()) }

    viewModel { InventoryViewModel() }
    viewModel { UnitOfMeasuresViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { WarehouseViewModel(get(), get(), get(), get(), get(), get()) }
}