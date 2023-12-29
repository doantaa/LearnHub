package com.cious.learnhub.data.network.api.service

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cious.learnhub.BuildConfig
import com.cious.learnhub.data.network.api.model.profile.ChangePasswordResponse
import com.cious.learnhub.data.network.api.model.profile.ChangePasswordRequest
import com.cious.learnhub.data.network.api.model.profile.ProfileRequest
import com.cious.learnhub.data.network.api.model.profile.ProfileResponse
import com.cious.learnhub.utils.SessionManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import java.io.IOException
import java.util.concurrent.TimeUnit

interface ProfileService {

    @GET("auth/profile")
    suspend fun  getDataUser(
    ): ProfileResponse

    @PATCH("auth/profile/edit/data")
    suspend fun editData(@Body profileRequest: ProfileRequest): ProfileResponse

    @PATCH("auth/profile/edit/ubah-password")
    suspend fun changePassword(@Body changePasswordRequest: ChangePasswordRequest): ChangePasswordResponse


    companion object {
        @JvmStatic
        operator fun invoke(sessionManager: SessionManager, chucker: ChuckerInterceptor): ProfileService {
            val token = sessionManager.getToken() ?: ""
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