package com.cious.learnhub.data.repository

import com.cious.learnhub.data.network.api.datasource.CourseDataSource
import com.cious.learnhub.data.network.api.model.category.toCategoryList
import com.cious.learnhub.data.network.api.model.course.toCourseList
import com.cious.learnhub.data.network.api.model.enrollments.Data
import com.cious.learnhub.model.Category
import com.cious.learnhub.model.Course
import com.cious.learnhub.utils.ResultWrapper
import com.cious.learnhub.utils.proceedFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart


interface CourseRepository {
    fun getCourses(
        category: String? = null,
        title: String? = null,
        courseType: String? = null,
        level: String? = null,
        popularity: String? = null
    ): Flow<ResultWrapper<List<Course>>>

    fun getCourses(
        category: List<String>? = null,
        title: String? = null,
        courseType: String? = null,
        level: String? = null
    ): Flow<ResultWrapper<List<Course>>>

    fun getCoursesById(
        id: Int
    ): Flow<ResultWrapper<Data>>

    fun getCategories(): Flow<ResultWrapper<List<Category>>>
}

class CourseRepositoryImpl(
    private val courseDataSource: CourseDataSource
) : CourseRepository {
    override fun getCourses(
        category: String?,
        title: String?,
        courseType: String?,
        level: String?,
        popularity: String?
    ): Flow<ResultWrapper<List<Course>>> {
        return proceedFlow {
            courseDataSource.getCourses(category, title, courseType, level, popularity).data?.toCourseList()
                ?: emptyList()
        }.onStart {
            emit(ResultWrapper.Loading())
        }
    }

    override fun getCourses(
        category: List<String>?,
        title: String?,
        courseType: String?,
        level: String?
    ): Flow<ResultWrapper<List<Course>>> {
        return proceedFlow {
            courseDataSource.getCourses(category, title, courseType, level).data?.toCourseList()
                ?: emptyList()
        }
    }

    override fun getCoursesById(
        id: Int
    ): Flow<ResultWrapper<Data>> {
        return proceedFlow {
            courseDataSource.getCoursesById(id).dataDetailResponse
        }
    }

    override fun getCategories(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow {
            courseDataSource.getCategory().data?.toCategoryList() ?: emptyList()
        }
    }
}