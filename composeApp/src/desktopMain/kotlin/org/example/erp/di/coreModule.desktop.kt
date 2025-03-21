package org.example.erp.di

import org.example.erp.core.util.Connectivity
import org.example.erp.core.util.ConnectivityImp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<Connectivity> {
        ConnectivityImp()
    }
}
