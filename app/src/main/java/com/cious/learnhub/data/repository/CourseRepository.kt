package com.cious.learnhub.data.repository

import com.cious.learnhub.data.network.api.datasource.CourseDataSource
import com.cious.learnhub.data.network.api.model.category.toCategoryList
import com.cious.learnhub.data.network.api.model.course.toCourseList
import com.cious.learnhub.model.Category
import com.cious.learnhub.model.Course
import com.cious.learnhub.utils.ResultWrapper
import com.cious.learnhub.utils.proceedFlow
import kotlinx.coroutines.flow.Flow


interface CourseRepository {
    fun getCourses(
        category: String? = null,
        title: String? = null,
        courseType: String? = null,
        level: String? = null
    ): Flow<ResultWrapper<List<Course>>>

    fun getCategories(): Flow<ResultWrapper<List<Category>>>
}

class CourseRepositoryImpl(
    private val courseDataSource: CourseDataSource
) : CourseRepository {
    override fun getCourses(
        category: String?,
        title: String?,
        courseType: String?,
        level: String?
    ): Flow<ResultWrapper<List<Course>>> {
        return proceedFlow {
            courseDataSource.getCourses(category, title, courseType, level).data?.toCourseList()
                ?: emptyList()
        }
    }

    override fun getCategories(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow {
            courseDataSource.getCategory().data?.categories?.toCategoryList() ?: emptyList()
        }
    }
}