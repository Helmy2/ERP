package org.example.erp.di

import kotlinx.coroutines.Dispatchers
import org.example.erp.core.data.core.AppDatabase
import org.example.erp.features.inventory.data.repository.CategoryRepoImpl
import org.example.erp.features.inventory.data.repository.ProductRepoImpl
import org.example.erp.features.inventory.data.repository.UnitOfMeasureRepoImpl
import org.example.erp.features.inventory.data.repository.WarehouseRepoImpl
import org.example.erp.features.inventory.domain.repository.CategoryRepo
import org.example.erp.features.inventory.domain.repository.ProductRepo
import org.example.erp.features.inventory.domain.repository.UnitOfMeasureRepo
import org.example.erp.features.inventory.domain.repository.WarehouseRepo
import org.example.erp.features.inventory.domain.useCase.category.CreateCategoryUseCase
import org.example.erp.features.inventory.domain.useCase.category.DeleteCategoryUseCase
import org.example.erp.features.inventory.domain.useCase.category.GetAllCategoryUseCase
import org.example.erp.features.inventory.domain.useCase.category.GetCategoryByCodeUseCase
import org.example.erp.features.inventory.domain.useCase.category.GetCategoryByIdUseCase
import org.example.erp.features.inventory.domain.useCase.category.SyncCategoriesUseCase
import org.example.erp.features.inventory.domain.useCase.category.UpdateCategoryUseCase
import org.example.erp.features.inventory.domain.useCase.product.CreateProductUseCase
import org.example.erp.features.inventory.domain.useCase.product.DeleteProductUseCase
import org.example.erp.features.inventory.domain.useCase.product.GetAllProductUseCase
import org.example.erp.features.inventory.domain.useCase.product.GetProductByCodeUseCase
import org.example.erp.features.inventory.domain.useCase.product.SyncProductsUseCase
import org.example.erp.features.inventory.domain.useCase.product.UpdateProductUseCase
import org.example.erp.features.inventory.domain.useCase.unitOfMeasures.CreateUnitOfMeasureUseCase
import org.example.erp.features.inventory.domain.useCase.unitOfMeasures.DeleteUnitOfMeasureUseCase
import org.example.erp.features.inventory.domain.useCase.unitOfMeasures.GetAllUnitsOfMeasureUseCase
import org.example.erp.features.inventory.domain.useCase.unitOfMeasures.GetUnitOfMeasuresByCodeUseCase
import org.example.erp.features.inventory.domain.useCase.unitOfMeasures.GetUnitOfMeasuresByIdUseCase
import org.example.erp.features.inventory.domain.useCase.unitOfMeasures.SyncUnitsOfMeasureUseCase
import org.example.erp.features.inventory.domain.useCase.unitOfMeasures.UpdateUnitOfMeasureUseCase
import org.example.erp.features.inventory.domain.useCase.warehouse.CreateWarehouseUseCase
import org.example.erp.features.inventory.domain.useCase.warehouse.DeleteWarehouseUseCase
import org.example.erp.features.inventory.domain.useCase.warehouse.GetAllWarehouseUseCase
import org.example.erp.features.inventory.domain.useCase.warehouse.GetWarehouseByCodeUseCase
import org.example.erp.features.inventory.domain.useCase.warehouse.SyncWarehouseUseCase
import org.example.erp.features.inventory.domain.useCase.warehouse.UpdateWarehouseUseCase
import org.example.erp.features.inventory.presentation.category.CategoryViewModel
import org.example.erp.features.inventory.presentation.inventory.InventoryViewModel
import org.example.erp.features.inventory.presentation.product.ProductViewModel
import org.example.erp.features.inventory.presentation.unitOfMeasures.UnitOfMeasuresViewModel
import org.example.erp.features.inventory.presentation.warehouses.WarehouseViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val inventoryModule = module {

    single { get<AppDatabase>().inventoryDao() }

    single { get<AppDatabase>().warehouseDao() }

    single { get<AppDatabase>().categoryDao() }

    single { get<AppDatabase>().productDao() }

    viewModel { InventoryViewModel() }

    viewModel { UnitOfMeasuresViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }
    viewModel { WarehouseViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }
    viewModel { CategoryViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get()) }
    viewModel { ProductViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get(),  get(), get(),get()) }


    single<UnitOfMeasureRepo> {
        UnitOfMeasureRepoImpl(get(), get(), Dispatchers.IO)
    }
    factory { SyncUnitsOfMeasureUseCase(get()) }
    factory { GetUnitOfMeasuresByCodeUseCase(get()) }
    factory { GetUnitOfMeasuresByIdUseCase(get()) }
    factory { CreateUnitOfMeasureUseCase(get()) }
    factory { GetAllUnitsOfMeasureUseCase(get()) }
    factory { UpdateUnitOfMeasureUseCase(get()) }
    factory { DeleteUnitOfMeasureUseCase(get()) }

    single<WarehouseRepo> {
        WarehouseRepoImpl(get(), get(), Dispatchers.IO)
    }
    factory { SyncWarehouseUseCase(get()) }
    factory { GetWarehouseByCodeUseCase(get()) }
    factory { CreateWarehouseUseCase(get()) }
    factory { GetAllWarehouseUseCase(get()) }
    factory { UpdateWarehouseUseCase(get()) }
    factory { DeleteWarehouseUseCase(get()) }

    single<CategoryRepo> {
        CategoryRepoImpl(get(), get(), Dispatchers.IO)
    }
    factory { SyncCategoriesUseCase(get()) }
    factory { GetCategoryByCodeUseCase(get()) }
    factory { GetCategoryByIdUseCase(get()) }
    factory { CreateCategoryUseCase(get()) }
    factory { GetAllCategoryUseCase(get()) }
    factory { UpdateCategoryUseCase(get()) }
    factory { DeleteCategoryUseCase(get()) }

    single<ProductRepo> {
        ProductRepoImpl(get(), get(), get(), get(), Dispatchers.IO)
    }
    factory { SyncProductsUseCase(get()) }
    factory { CreateProductUseCase(get()) }
    factory { GetAllProductUseCase(get()) }
    factory { GetProductByCodeUseCase(get()) }
    factory { UpdateProductUseCase(get()) }
    factory { DeleteProductUseCase(get()) }


}