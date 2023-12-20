package com.cious.learnhub.data.network.api.service

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cious.learnhub.BuildConfig
import com.cious.learnhub.data.network.api.model.category.CategoriesResponse
import com.cious.learnhub.data.network.api.model.enrollments.EnrollmentsResponse
import com.cious.learnhub.utils.SessionManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException
import java.util.concurrent.TimeUnit

interface EnrollmentService {
    @GET("enrollment")
    suspend fun getEnrollments(
        @Query("categoryIds") category: String? = null,
        @Query("title") title: String? = null,
        @Query("courseType") courseType: String? = null,
        @Query("level") level: String? = null
    ): EnrollmentsResponse

    @GET("enrollment/{id}")
    suspend fun getCourseById(
        @Query("id") id: Int
    ): EnrollmentsResponse

    @GET("category")
    suspend fun getCategories(): CategoriesResponse

    companion object {
        @JvmStatic
        operator fun invoke(chucker: ChuckerInterceptor, context: Context): EnrollmentService {
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
            return retrofit.create(EnrollmentService::class.java)
        }
    }
}