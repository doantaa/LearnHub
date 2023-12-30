package com.cious.learnhub.data.network.api.service

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cious.learnhub.BuildConfig
import com.cious.learnhub.data.network.api.model.category.CategoriesResponse
import com.cious.learnhub.data.network.api.model.course.CoursesResponse
import com.cious.learnhub.data.network.api.model.enrollments.EnrollmentDetailResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface CourseService {
    @GET("course")
    suspend fun getCourses(
        @Query("categoryIds") category: String? = null,
        @Query("title") title: String? = null,
        @Query("courseType") courseType: String? = null,
        @Query("level") level: String? = null,
        @Query("popularity") popularity: String? = null
    ): CoursesResponse

    @GET("course")
    suspend fun getCourses(
        @Query("categoryIds") category: List<String>? = null,
        @Query("title") title: String? = null,
        @Query("courseType") courseType: String? = null,
        @Query("level") level: String? = null,
    ): CoursesResponse

    @GET("course/{id}")
    suspend fun getCourseById(
        @Path("id") id: Int
    ): EnrollmentDetailResponse

    @GET("category")
    suspend fun getCategories(): CategoriesResponse

    companion object {
        @JvmStatic
        operator fun invoke(chucker: ChuckerInterceptor): CourseService {
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
            return retrofit.create(CourseService::class.java)
        }

    }
}