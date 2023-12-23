package com.cious.learnhub.data.network.api.datasource

import com.cious.learnhub.data.network.api.model.category.CategoriesResponse
import com.cious.learnhub.data.network.api.model.enrollments.EnrollmentsResponse
import com.cious.learnhub.data.network.api.service.EnrollmentService

interface EnrollmentDataSource {
    suspend fun getEnrollment(
        category: String? = null,
        title: String? = null,
        courseType: String? = null,
        level: String? = null
    ): EnrollmentsResponse

    suspend fun getCoursesById(
        id: Int
    ): EnrollmentsResponse

    suspend fun getCategory(): CategoriesResponse
}

class EnrollmentApiDataSource(private val service: EnrollmentService) : EnrollmentDataSource {
    override suspend fun getEnrollment(
        category: String?,
        title: String?,
        courseType: String?,
        level: String?
    ): EnrollmentsResponse {
        return service.getEnrollments(category, title, courseType, level)
    }

    override suspend fun getCoursesById(id: Int): EnrollmentsResponse {
        return service.getCourseById(id)
    }

    override suspend fun getCategory(): CategoriesResponse {
        return service.getCategories()
    }

}