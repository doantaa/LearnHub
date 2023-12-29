package com.cious.learnhub.data.network.api.datasource

import com.cious.learnhub.data.network.api.model.category.CategoriesResponse
import com.cious.learnhub.data.network.api.model.course.CourseDetailResponse
import com.cious.learnhub.data.network.api.model.course.CoursesResponse
import com.cious.learnhub.data.network.api.model.enrollments.EnrollmentDetailResponse
import com.cious.learnhub.data.network.api.service.CourseService

interface CourseDataSource {
    suspend fun getCourses(
        category: String? = null,
        title: String? = null,
        courseType: String? = null,
        level: String? = null
    ): CoursesResponse

    suspend fun getCourses(
        category: List<String>? = null,
        title: String? = null,
        courseType: String? = null,
        level: String? = null
    ): CoursesResponse

    suspend fun getCoursesById(
        id: Int
    ): EnrollmentDetailResponse


    suspend fun getCategory(): CategoriesResponse
}

class CourseApiDataSource(
    private val service: CourseService
) : CourseDataSource {
    override suspend fun getCourses(
        category: String?,
        title: String?,
        courseType: String?,
        level: String?
    ): CoursesResponse {
        return service.getCourses(category, title, courseType, level)
    }

    override suspend fun getCourses(
        category: List<String>?,
        title: String?,
        courseType: String?,
        level: String?
    ): CoursesResponse {
        return service.getCourses(category, title, courseType, level)
    }


    override suspend fun getCoursesById(
        id: Int
    ): EnrollmentDetailResponse {
        return service.getCourseById(id)
    }

    override suspend fun getCategory(): CategoriesResponse {
        return service.getCategories()
    }

}