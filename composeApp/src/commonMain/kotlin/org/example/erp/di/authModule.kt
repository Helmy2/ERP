package org.example.erp.di

import kotlinx.coroutines.Dispatchers
import org.example.erp.features.auth.data.exception.AuthExceptionMapper
import org.example.erp.features.auth.data.repository.AuthRepoImpl
import org.example.erp.features.auth.domain.repository.AuthRepo
import org.example.erp.features.auth.domain.usecase.IsUserLongedInFlowUseCase
import org.example.erp.features.auth.domain.usecase.IsUserLongedInUseCase
import org.example.erp.features.auth.domain.usecase.LoginUseCase
import org.example.erp.features.auth.domain.usecase.RegisterUseCase
import org.example.erp.features.auth.presentation.login.LoginViewModel
import org.example.erp.features.auth.presentation.register.RegisterViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val authModule = module {

    single<AuthRepo> {
        AuthRepoImpl(
            supabaseClient = get(),
            exceptionMapper = AuthExceptionMapper(),
            dispatcher = Dispatchers.IO
        )
    }

    factory { IsUserLongedInUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { RegisterUseCase(get()) }
    factory { IsUserLongedInFlowUseCase(get()) }

    viewModel { LoginViewModel(get(), get(), get(),get()) }
    viewModel { RegisterViewModel(get(), get(), get()) }
}



