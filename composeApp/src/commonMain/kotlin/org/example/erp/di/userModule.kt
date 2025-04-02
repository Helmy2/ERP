package org.example.erp.di

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.minimalSettings
import io.github.jan.supabase.createSupabaseClient
import kotlinx.coroutines.Dispatchers
import org.example.erp.BuildKonfig
import org.example.erp.core.domain.usecase.ChangeLanguageUseCase
import org.example.erp.core.domain.usecase.GetLanguageUseCase
import org.example.erp.features.user.data.exception.AuthExceptionMapper
import org.example.erp.features.user.data.repository.UserRepoImpl
import org.example.erp.features.user.domain.repository.UserRepo
import org.example.erp.features.user.domain.usecase.CurrentUserFlowUseCase
import org.example.erp.features.user.domain.usecase.GetDisplayNameUseCase
import org.example.erp.features.user.domain.usecase.IsUserLongedInFlowUseCase
import org.example.erp.features.user.domain.usecase.IsUserLongedInUseCase
import org.example.erp.features.user.domain.usecase.LoginUseCase
import org.example.erp.features.user.domain.usecase.LogoutUseCase
import org.example.erp.features.user.domain.usecase.RegisterUseCase
import org.example.erp.features.user.domain.usecase.UpdateNameUseCase
import org.example.erp.features.user.presentation.login.LoginViewModel
import org.example.erp.features.user.presentation.setting.SettingsViewModel
import org.example.erp.features.user.presentation.register.RegisterViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val userModule = module {

    single<UserRepo> {
        val adminClient = createSupabaseClient(
            supabaseKey = BuildKonfig.supabaseKey,
            supabaseUrl = BuildKonfig.supabaseUrl
        ) {
            install(Auth) {
                minimalSettings()
            }
        }

        UserRepoImpl(
            supabaseClient = get(),
            adminClient = adminClient,
            exceptionMapper = AuthExceptionMapper(),
            dispatcher = Dispatchers.IO
        )
    }

    factory { IsUserLongedInUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { RegisterUseCase(get()) }
    factory { IsUserLongedInFlowUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { UpdateNameUseCase(get()) }
    factory { CurrentUserFlowUseCase(get()) }
    factory { ChangeLanguageUseCase(get()) }
    factory { GetLanguageUseCase(get()) }
    factory { GetDisplayNameUseCase(get()) }

    viewModel { LoginViewModel(get(), get(), get(), get()) }
    viewModel { RegisterViewModel(get(), get(), get()) }
    viewModel { SettingsViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get()) }
}



