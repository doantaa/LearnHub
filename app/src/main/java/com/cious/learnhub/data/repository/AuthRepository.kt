package com.cious.learnhub.data.repository

import com.cious.learnhub.data.network.api.datasource.AuthDataSource
import com.cious.learnhub.data.network.api.model.login.LoginRequest
import com.cious.learnhub.data.network.api.model.login.toLoginData
import com.cious.learnhub.data.network.api.model.otp.OtpRequest
import com.cious.learnhub.data.network.api.model.register.RegisterRequest
import com.cious.learnhub.data.network.api.model.register.toRegisterData
import com.cious.learnhub.model.AuthenticationData
import com.cious.learnhub.model.LoginData
import com.cious.learnhub.model.RegisterData
import com.cious.learnhub.utils.ResultWrapper
import com.cious.learnhub.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun sendOtpRequest(email: String): Flow<ResultWrapper<String>>
    suspend fun doRegister(
        authenticationData: AuthenticationData,
        otp: String
    ): Flow<ResultWrapper<RegisterData>>

    suspend fun doLogin(loginRequest: LoginRequest): Flow<ResultWrapper<LoginData>>
}

class AuthRepositoryImpl(
    private val dataSource: AuthDataSource
) : AuthRepository {
    override suspend fun sendOtpRequest(email: String): Flow<ResultWrapper<String>> {
        return proceedFlow {
            val otpRequest = OtpRequest(email)
            val otpResult = dataSource.getOtp(otpRequest)
            otpResult.data
        }
    }

    override suspend fun doRegister(
        authenticationData: AuthenticationData,
        otp: String
    ): Flow<ResultWrapper<RegisterData>> {
        return proceedFlow {
            val registerRequest = RegisterRequest(
                name = authenticationData.name,
                email = authenticationData.email,
                phoneNumber = authenticationData.phoneNumber,
                password = authenticationData.password,
                otp = otp,
                hashedOtp = authenticationData.hashOtp
            )
            dataSource.doRegister(registerRequest).toRegisterData()
        }
    }

    override suspend fun doLogin(loginRequest: LoginRequest): Flow<ResultWrapper<LoginData>> {
        return proceedFlow {
            return@proceedFlow dataSource.doLogin(loginRequest).toLoginData()
        }
    }
}