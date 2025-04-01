package org.example.erp.di

import kotlinx.coroutines.Dispatchers
import org.example.erp.core.data.core.AppDatabase
import org.example.erp.features.inventory.data.repository.InventoryRepsImpl
import org.example.erp.features.inventory.domain.repository.InventoryReps
import org.example.erp.features.inventory.domain.useCase.category.CreateCategoryUseCase
import org.example.erp.features.inventory.domain.useCase.category.DeleteCategoryUseCase
import org.example.erp.features.inventory.domain.useCase.category.GetAllCategoryUseCase
import org.example.erp.features.inventory.domain.useCase.category.UpdateCategoryUseCase
import org.example.erp.features.inventory.domain.useCase.unitOfMeasures.CreateUnitOfMeasureUseCase
import org.example.erp.features.inventory.domain.useCase.unitOfMeasures.DeleteUnitOfMeasureUseCase
import org.example.erp.features.inventory.domain.useCase.unitOfMeasures.GetAllUnitsOfMeasureUseCase
import org.example.erp.features.inventory.domain.useCase.unitOfMeasures.UpdateUnitOfMeasureUseCase
import org.example.erp.features.inventory.domain.useCase.warehouse.CreateWarehouseUseCase
import org.example.erp.features.inventory.domain.useCase.warehouse.DeleteWarehouseUseCase
import org.example.erp.features.inventory.domain.useCase.warehouse.GetAllWarehouseUseCase
import org.example.erp.features.inventory.domain.useCase.warehouse.UpdateWarehouseUseCase
import org.example.erp.features.inventory.presentation.category.CategoryViewModel
import org.example.erp.features.inventory.presentation.inventory.InventoryViewModel
import org.example.erp.features.inventory.presentation.unitOfMeasures.UnitOfMeasuresViewModel
import org.example.erp.features.inventory.presentation.warehouses.WarehouseViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val inventoryModule = module {
    single<InventoryReps> {
        InventoryRepsImpl(
            get(), get(), get(), get(),
            dispatcher = Dispatchers.IO,
        )
    }

    factory {
        get<AppDatabase>().inventoryDao()
    }
    factory {
        get<AppDatabase>().warehouseDao()
    }

    factory {
        get<AppDatabase>().categoryDao()
    }

    factory { CreateUnitOfMeasureUseCase(get()) }
    factory { GetAllUnitsOfMeasureUseCase(get()) }
    factory { UpdateUnitOfMeasureUseCase(get()) }
    factory { DeleteUnitOfMeasureUseCase(get()) }

    factory { CreateWarehouseUseCase(get()) }
    factory { GetAllWarehouseUseCase(get()) }
    factory { UpdateWarehouseUseCase(get()) }
    factory { DeleteWarehouseUseCase(get()) }

    factory { CreateCategoryUseCase(get()) }
    factory { GetAllCategoryUseCase(get()) }
    factory { UpdateCategoryUseCase(get()) }
    factory { DeleteCategoryUseCase(get()) }

    viewModel { InventoryViewModel() }
    viewModel { UnitOfMeasuresViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { WarehouseViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { CategoryViewModel(get(), get(), get(), get(), get(), get()) }
}