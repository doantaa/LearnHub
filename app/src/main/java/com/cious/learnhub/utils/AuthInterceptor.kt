package com.cious.learnhub.utils

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val sessionManager: SessionManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequestBuilder = chain
            .request()
            .newBuilder()
            .apply { 
                sessionManager.getToken()?.let { token ->
                    addHeader("Authorization","Bearer $token")
                }
            }.build()
        return chain.proceed(newRequestBuilder)
    }
}