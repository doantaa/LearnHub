package com.cious.learnhub.data.network.api.datasource

import com.cious.learnhub.data.network.api.model.login.LoginRequest
import com.cious.learnhub.data.network.api.model.login.LoginResponse
import com.cious.learnhub.data.network.api.model.otp.OtpRequest
import com.cious.learnhub.data.network.api.model.otp.OtpResponse
import com.cious.learnhub.data.network.api.model.register.RegisterRequest
import com.cious.learnhub.data.network.api.model.register.RegisterResponse
import com.cious.learnhub.data.network.api.service.AuthenticationService


interface AuthDataSource {
    suspend fun sendOtpRegister(
        email: OtpRequest
    ): OtpResponse

    suspend fun doRegister(
        registerRequest: RegisterRequest
    ): RegisterResponse

    suspend fun doLogin(
        loginRequest: LoginRequest
    ): LoginResponse

    suspend fun sendOtpResetPassword(
        email: OtpRequest
    ): OtpResponse
}

class AuthDataSourceImpl(
    private val service: AuthenticationService
) : AuthDataSource {
    override suspend fun sendOtpRegister(
        email: OtpRequest
    ): OtpResponse {
        return service.otpRegister(email)
    }

    override suspend fun doRegister(
        registerRequest: RegisterRequest
    ): RegisterResponse {
        return service.register(registerRequest)
    }

    override suspend fun doLogin(
        loginRequest: LoginRequest
    ): LoginResponse {
        return service.login(loginRequest)
    }

    override suspend fun sendOtpResetPassword(
        email: OtpRequest
    ): OtpResponse {
        return service.otpResetPassword(email)
    }
}