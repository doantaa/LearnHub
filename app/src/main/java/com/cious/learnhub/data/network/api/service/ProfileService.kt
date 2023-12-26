package com.cious.learnhub.data.network.api.service

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cious.learnhub.BuildConfig
import com.cious.learnhub.data.network.api.model.profile.ProfileResponse
import com.cious.learnhub.utils.SessionManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.IOException
import java.util.concurrent.TimeUnit

interface ProfileService {

    @GET("auth/profile")
    suspend fun  getDataUser(
    ): ProfileResponse


    companion object {
        @JvmStatic
        operator fun invoke(chucker: ChuckerInterceptor, context: Context): ProfileService {
            val token = SessionManager.getToken(context) ?: ""
            val client = OkHttpClient.Builder()
                .addInterceptor(chucker)
                .addInterceptor(object : Interceptor {
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                        val newRequest = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer $token")
                            .build()
                        return chain.proceed(newRequest)
                    }
                })
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ProfileService::class.java)
        }
    }
}



