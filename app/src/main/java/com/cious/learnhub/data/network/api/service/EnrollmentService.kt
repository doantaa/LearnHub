package com.cious.learnhub.data.network.api.service

import android.util.Log
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cious.learnhub.BuildConfig
import com.cious.learnhub.data.network.api.model.category.CategoriesResponse
import com.cious.learnhub.data.network.api.model.enrollments.EnrollmentDetailResponse
import com.cious.learnhub.data.network.api.model.enrollments.EnrollmentsResponse
import com.cious.learnhub.data.network.api.model.enrollments.ProgressResponse
import com.cious.learnhub.utils.SessionManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.IOException
import java.util.concurrent.TimeUnit

interface EnrollmentService {
    @GET("enrollment")
    suspend fun getEnrollments(
        @Query("categoryId") category: String? = null,
        @Query("title") title: String? = null,
        @Query("courseType") courseType: String? = null,
        @Query("level") level: String? = null
    ): EnrollmentsResponse

    @GET("enrollment/{id}")
    suspend fun getCourseById(
        @Path("id") id: Int
    ): EnrollmentDetailResponse

    @POST("progress/{id}")
    suspend fun postProgress(@Path("id") id: Int) : ProgressResponse

    @GET("category")
    suspend fun getCategories(): CategoriesResponse

    companion object {
        @JvmStatic
        operator fun invoke(sessionManager: SessionManager, chucker: ChuckerInterceptor): EnrollmentService {
            val token = sessionManager.getToken() ?: ""
            val client = OkHttpClient.Builder()
                .addInterceptor(chucker)
                .addInterceptor(object : Interceptor {
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                        Log.d("TOKEN SERVICE ENROLLMENT", token)
                        val newRequest = chain.request().newBuilder()
                            .addHeader("Authorization", token)
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