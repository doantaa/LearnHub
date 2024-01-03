package com.cious.learnhub.data.repository

import com.cious.learnhub.data.network.api.datasource.AuthDataSource
import com.cious.learnhub.data.network.api.model.login.LoginRequest
import com.cious.learnhub.data.network.api.model.login.toLoginData
import com.cious.learnhub.data.network.api.model.otp.OtpRequest
import com.cious.learnhub.data.network.api.model.otp.toOtpData
import com.cious.learnhub.data.network.api.model.register.RegisterRequest
import com.cious.learnhub.data.network.api.model.register.toRegisterData
import com.cious.learnhub.data.network.api.model.resetpassword.VerifyResetPasswordRequest
import com.cious.learnhub.data.network.api.model.resetpassword.toVerifyResetPasswordData
import com.cious.learnhub.model.AuthenticationData
import com.cious.learnhub.model.LoginData
import com.cious.learnhub.model.OtpData
import com.cious.learnhub.model.RegisterData
import com.cious.learnhub.model.VerifyResetPasswordData
import com.cious.learnhub.utils.ResultWrapper
import com.cious.learnhub.utils.SessionManager
import com.cious.learnhub.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun sendOtpRegister(
        otpRequest: OtpRequest
    ): Flow<ResultWrapper<OtpData>>

    fun doRegister(
        authenticationData: AuthenticationData,
        otp: String
    ): Flow<ResultWrapper<RegisterData>>

    fun doLogin(
        loginRequest: LoginRequest
    ): Flow<ResultWrapper<LoginData>>


    fun sendOtpPassword(
        otpRequest: OtpRequest
    ): Flow<ResultWrapper<OtpData>>

    fun doResetPassword(
        verifyResetPasswordRequest: VerifyResetPasswordRequest
    ): Flow<ResultWrapper<VerifyResetPasswordData>>

    fun isLogin(): Boolean

    fun clearToken()
}

class AuthRepositoryImpl(
    private val dataSource: AuthDataSource,
    private val sessionManager: SessionManager
) : AuthRepository {
    override fun sendOtpRegister(
        otpRequest: OtpRequest
    ): Flow<ResultWrapper<OtpData>> {
        return proceedFlow {
            dataSource.sendOtpRegister(otpRequest).toOtpData()
        }
    }

    override fun doRegister(
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
            val result = dataSource.doRegister(registerRequest).toRegisterData()
            sessionManager.saveAuthToken(result.token)
            result
        }
    }

    override fun doLogin(
        loginRequest: LoginRequest
    ): Flow<ResultWrapper<LoginData>> {
        return proceedFlow {
            val result = dataSource.doLogin(loginRequest).toLoginData()
            sessionManager.saveAuthToken(result.token)
            result
        }
    }

    override fun sendOtpPassword(
        otpRequest: OtpRequest
    ): Flow<ResultWrapper<OtpData>> {
        return proceedFlow {
            dataSource.sendOtpResetPassword(otpRequest).toOtpData()
        }
    }

    override fun doResetPassword(
        verifyResetPasswordRequest: VerifyResetPasswordRequest
    ): Flow<ResultWrapper<VerifyResetPasswordData>> {
        return proceedFlow {
            dataSource.doResetPassword(verifyResetPasswordRequest).toVerifyResetPasswordData()
        }
    }

    override fun isLogin(): Boolean {
        return !sessionManager.getToken().isNullOrEmpty()
    }

    override fun clearToken() {
        sessionManager.clearData()
    }

}