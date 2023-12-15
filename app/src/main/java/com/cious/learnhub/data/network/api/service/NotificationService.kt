package com.cious.learnhub.data.network.api.service

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cious.learnhub.BuildConfig
import com.cious.learnhub.data.network.api.model.notification.NotificationResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface NotificationService {


    @GET("notification/my")
    suspend fun getNotification(
        @Header("Authorization") token: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTIsIm5hbWUiOiJpenp1ZGluIiwiZW1haWwiOiJtb2hhbW1hZHp6dWRpbkBnbWFpbC5jb20iLCJyb2xlIjoidXNlciIsImlhdCI6MTcwMjY2NDc1MiwiZXhwIjoxNzAyNzUxMTUyfQ.XTYgIFt9iIcyErNh68Pv4e88EV-fvVk5CkqNMSpTQYU"
    ) : NotificationResponse

    companion object {
        @JvmStatic
        operator fun invoke(chucker: ChuckerInterceptor): NotificationService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(chucker)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(NotificationService::class.java)
        }

    }
}
