package org.example.erp.di

import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module


val appModule = module {
    includes(coreModule)
    includes(supabaseModule)
    includes(userModule)
}
