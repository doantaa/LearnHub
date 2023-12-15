package com.cious.learnhub.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cious.learnhub.data.UserPreferenceDataStore
import com.cious.learnhub.data.UserPreferenceDataStoreImpl
import com.cious.learnhub.data.network.api.datasource.CourseApiDataSouce
import com.cious.learnhub.data.network.api.datasource.CourseDataSource
import com.cious.learnhub.data.network.api.service.CourseService
import com.cious.learnhub.data.repository.CourseRepository
import com.cious.learnhub.data.repository.CourseRepositoryImpl
import com.cious.learnhub.ui.authentication.login.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {

    private val localModule = module {

    }

    private val networkModule = module {
        single { ChuckerInterceptor(androidContext()) }
        single { CourseService.invoke(get()) }
    }

    private val dataSourceModule = module {
        single<CourseDataSource> { CourseApiDataSouce(get()) }
    }

    private val repositoryModule = module {
        single<CourseRepository> { CourseRepositoryImpl(get()) }
    }

    private val appModule = module {
        single<UserPreferenceDataStore> { UserPreferenceDataStoreImpl(androidContext()) }
        viewModel { LoginViewModel(get(), get()) }
    }

    val modules: List<Module> = listOf (
        localModule,
        networkModule,
        dataSourceModule,
        repositoryModule,
        appModule
    )
}