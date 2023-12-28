package com.cious.learnhub.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cious.learnhub.data.network.api.datasource.AuthDataSource
import com.cious.learnhub.data.network.api.datasource.AuthDataSourceImpl
import com.cious.learnhub.data.network.api.datasource.CourseApiDataSource
import com.cious.learnhub.data.network.api.datasource.CourseDataSource
import com.cious.learnhub.data.network.api.datasource.NotificaitonDataSource
import com.cious.learnhub.data.network.api.datasource.NotificationDataSourceImpl
import com.cious.learnhub.data.network.api.datasource.PaymentApiDataSource
import com.cious.learnhub.data.network.api.datasource.PaymentDataSource
import com.cious.learnhub.data.network.api.service.AuthenticationService
import com.cious.learnhub.data.network.api.service.CourseService
import com.cious.learnhub.data.network.api.service.NotificationService
import com.cious.learnhub.data.network.api.service.PaymentService
import com.cious.learnhub.data.repository.AuthRepository
import com.cious.learnhub.data.repository.AuthRepositoryImpl
import com.cious.learnhub.data.repository.CourseRepository
import com.cious.learnhub.data.repository.CourseRepositoryImpl
import com.cious.learnhub.data.repository.NotifiacationRepository
import com.cious.learnhub.data.repository.NotificationRepositoryImpl
import com.cious.learnhub.data.repository.PaymentRepository
import com.cious.learnhub.data.repository.PaymentRepositoryImpl
import com.cious.learnhub.ui.authentication.login.LoginViewModel
import com.cious.learnhub.ui.authentication.otp.OtpViewModel
import com.cious.learnhub.ui.authentication.register.RegisterViewModel
import com.cious.learnhub.ui.authentication.resetpassword.OtpPasswordViewModel
import com.cious.learnhub.ui.authentication.resetpassword.ResetPasswordViewModel
import com.cious.learnhub.ui.authentication.resetpassword.VerifyResetPasswordViewModel
import com.cious.learnhub.ui.course.CourseViewModel
import com.cious.learnhub.ui.detail.CourseDetailViewModel
import com.cious.learnhub.ui.home.HomeViewModel
import com.cious.learnhub.ui.home.search.HomeSearchViewModel
import com.cious.learnhub.ui.notifications.NotificationsViewModel
import com.cious.learnhub.ui.payment.detail.PaymentViewModel
import com.cious.learnhub.ui.payment.midtrans.PaymentMidtransViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {

    private val localModule = module {

    }

    private val networkModule = module {
        single { ChuckerInterceptor(androidContext()) }
        single { CourseService.invoke(get()) }
        single { AuthenticationService.invoke(get(), androidContext()) }
        single { NotificationService.invoke(get(), androidContext()) }
        single { PaymentService.invoke(get(), androidContext()) }

    }

    private val dataSourceModule = module {
        single<CourseDataSource> { CourseApiDataSource(get()) }
        single<AuthDataSource> { AuthDataSourceImpl(get()) }
        single<NotificaitonDataSource> { NotificationDataSourceImpl(get()) }
        single<PaymentDataSource> { PaymentApiDataSource(get()) }
    }

    private val repositoryModule = module {
        single<CourseRepository> { CourseRepositoryImpl(get()) }
        single<AuthRepository> { AuthRepositoryImpl(get()) }
        single<NotifiacationRepository> { NotificationRepositoryImpl(get()) }
        single<PaymentRepository> { PaymentRepositoryImpl(get()) }
    }

    private val viewModelModule = module {
        viewModelOf(::HomeViewModel)
        viewModelOf(::CourseViewModel)
        viewModelOf(::LoginViewModel)
        viewModel { params -> OtpViewModel(extras = params.get(), get()) }
        viewModelOf(::RegisterViewModel)
        viewModelOf(::NotificationsViewModel)
        viewModel{ params -> PaymentViewModel( get(), extras = params.get()) }
        viewModelOf(::CourseDetailViewModel)
        viewModelOf(::ResetPasswordViewModel)
        viewModel { params -> OtpPasswordViewModel(extras = params.get()) }
        viewModel { params -> VerifyResetPasswordViewModel(extras = params.get(), get()) }
        viewModelOf(::PaymentMidtransViewModel)
        viewModelOf(::HomeSearchViewModel)
    }

    val modules: List<Module> = listOf(
        localModule,
        networkModule,
        dataSourceModule,
        repositoryModule,
        viewModelModule
    )
}