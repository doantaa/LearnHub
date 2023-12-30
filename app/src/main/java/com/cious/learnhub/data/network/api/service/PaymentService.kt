package com.cious.learnhub.data.network.api.service

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cious.learnhub.BuildConfig
import com.cious.learnhub.data.network.api.model.payment.PaymentResponse
import com.cious.learnhub.utils.AuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface PaymentService {

    @POST("payment/{id}")
    suspend fun createPayment(@Path("id") id: Int): PaymentResponse

    companion object {
        @JvmStatic
        operator fun invoke(authInterceptor: AuthInterceptor, chucker: ChuckerInterceptor): PaymentService {
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
            return retrofit.create(PaymentService::class.java)
        }
    }
}


