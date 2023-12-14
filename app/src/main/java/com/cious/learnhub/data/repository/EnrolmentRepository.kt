package com.cious.learnhub.data.repository

import com.cious.learnhub.data.network.api.datasource.EnrollmentDataSource
import com.cious.learnhub.data.network.api.model.category.toCategoryList
import com.cious.learnhub.data.network.api.model.enrollments.toEnrollmentList
import com.cious.learnhub.data.network.api.service.CourseService
import com.cious.learnhub.model.Category
import com.cious.learnhub.model.Course
import com.cious.learnhub.model.Enrollment
import com.cious.learnhub.utils.ResultWrapper
import com.cious.learnhub.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart


interface EnrollmentRepository{
    fun getEnrollment(
        category: String? = null,
        title: String? = null,
        courseType: String? = null,
        level: String? = null
    ): Flow<ResultWrapper<List<Enrollment>>>
    fun getCategories(): Flow<ResultWrapper<List<Category>>>
}

class EnrollmentRepositoryImpl(
    private val enrollmentDataSource: EnrollmentDataSource
): EnrollmentRepository{
    override fun getEnrollment(
        category: String?,
        title: String?,
        courseType: String?,
        level: String?
    ): Flow<ResultWrapper<List<Enrollment>>> {
        return proceedFlow {
            enrollmentDataSource.getEnrollment(category, title, courseType, level).data?.toEnrollmentList()
                ?: emptyList()
        }.onStart {
            emit(ResultWrapper.Loading())
            delay(2000)
        }
    }

    override fun getCategories(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow {
            enrollmentDataSource.getCategory().data?.categories?.toCategoryList() ?: emptyList()
        }
    }

}
