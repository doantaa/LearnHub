package com.cious.learnhub.data.network.api.datasource

import android.icu.text.CaseMap.Title
import com.cious.learnhub.data.network.api.model.category.CategoriesResponse
import com.cious.learnhub.data.network.api.model.course.CoursesResponse
import com.cious.learnhub.data.network.api.service.CourseService
import com.cious.learnhub.model.Category

interface CourseDataSource {
    suspend fun getCourses(
        category: String? = null,
        title: String? = null,
        level: String? = null
    ) : CoursesResponse

    suspend fun getCategory() : CategoriesResponse
}

class CourseApiDataSouce(
    private val service: CourseService
) : CourseDataSource {
    override suspend fun getCourses(
        category: String?,
        title: String?,
        level: String?
    ): CoursesResponse {
        return service.getCourses(category, title, level)
    }

    override suspend fun getCategory(): CategoriesResponse {
        return service.getCategories()
    }

}