package com.cious.learnhub.data.network.api.service

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cious.learnhub.BuildConfig
import com.cious.learnhub.data.network.api.model.login.LoginRequest
import com.cious.learnhub.data.network.api.model.login.LoginResponse
import com.cious.learnhub.data.network.api.model.otp.OtpRequest
import com.cious.learnhub.data.network.api.model.otp.OtpResponse
import com.cious.learnhub.data.network.api.model.register.RegisterRequest
import com.cious.learnhub.data.network.api.model.register.RegisterResponse
import com.cious.learnhub.data.network.api.model.resetpassword.VerifyResetPasswordRequest
import com.cious.learnhub.data.network.api.model.resetpassword.VerifyResetPasswordResponse
import com.cious.learnhub.utils.AuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface AuthenticationService {

    @POST("otp")
    suspend fun otpRegister(@Body email: OtpRequest): OtpResponse

    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("reset-password/otp")
    suspend fun otpResetPassword(@Body email: OtpRequest): OtpResponse

    @POST("reset-password/verify")
    suspend fun resetPassword(@Body verifyResetPasswordRequest: VerifyResetPasswordRequest): VerifyResetPasswordResponse

    companion object {
        @JvmStatic
        operator fun invoke(authInterceptor: AuthInterceptor, chucker: ChuckerInterceptor): AuthenticationService {
            val client = OkHttpClient.Builder()
                .addInterceptor(chucker)
                .addInterceptor(authInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(AuthenticationService::class.java)
        }
    }
}

