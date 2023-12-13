package com.cious.learnhub.data.network.api.datasource

import com.cious.learnhub.data.network.api.model.course.CoursesResponse
import com.cious.learnhub.data.network.api.model.enrollments.EnrollmentsResponse
import com.cious.learnhub.data.network.api.service.EnrollmentService

interface EnrollmentDataSource {
    suspend fun getEnrollment(
        category: String? = null,
        title: String? = null,
        courseType: String? = null,
        level: String? = null
    ): EnrollmentsResponse
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

}