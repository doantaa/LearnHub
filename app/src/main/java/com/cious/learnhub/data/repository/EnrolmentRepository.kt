package com.cious.learnhub.data.repository

import android.content.Context
import com.cious.learnhub.data.network.api.datasource.CourseDataSource
import com.cious.learnhub.data.network.api.datasource.EnrollmentDataSource
import com.cious.learnhub.data.network.api.model.category.toCategoryList
import com.cious.learnhub.data.network.api.model.course.toCourse
import com.cious.learnhub.data.network.api.model.enrollments.toEnrollment
import com.cious.learnhub.data.network.api.model.enrollments.toEnrollmentList
import com.cious.learnhub.data.network.api.model.enrollments.toProgress
import com.cious.learnhub.model.Category
import com.cious.learnhub.model.Enrollment
import com.cious.learnhub.model.Progress
import com.cious.learnhub.utils.ResultWrapper
import com.cious.learnhub.utils.SessionManager
import com.cious.learnhub.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart


interface EnrollmentRepository {
    fun getEnrollment(
        category: String? = null,
        title: String? = null,
        courseType: String? = null,
        level: String? = null
    ): Flow<ResultWrapper<List<Enrollment>>>

    fun getCoursesById(
        id: Int
    ): Flow<ResultWrapper<Enrollment>>

    suspend fun postProgress(
        id: Int
    ): Flow<ResultWrapper<Progress>>

    fun getCategories(): Flow<ResultWrapper<List<Category>>>
}

class EnrollmentRepositoryImpl(
    private val enrollmentDataSource: EnrollmentDataSource,
    private val courseDataSource: CourseDataSource,
    private val context: Context
) : EnrollmentRepository {
    override fun getEnrollment(
        category: String?,
        title: String?,
        courseType: String?,
        level: String?
    ): Flow<ResultWrapper<List<Enrollment>>> {
        return proceedFlow {
            enrollmentDataSource.getEnrollment(
                category,
                title,
                courseType,
                level
            ).data?.toEnrollmentList()
                ?: emptyList()
        }.onStart {
            emit(ResultWrapper.Loading())
            delay(2000)
        }
    }

    override fun getCoursesById(id: Int): Flow<ResultWrapper<Enrollment>> {
        return proceedFlow {
            val token = SessionManager.getToken(context)
            if (token != null) {
                enrollmentDataSource.getCoursesById(id).dataDetailResponse.toEnrollment()
            } else {
                courseDataSource.getCoursesById(id).dataDetailResponse.toEnrollment()
            }
        }.onStart {
            emit(ResultWrapper.Loading())
            delay(2000)
        }
    }

    override suspend fun postProgress(id: Int): Flow<ResultWrapper<Progress>> {
        return proceedFlow {
            enrollmentDataSource.postProgress(id).toProgress()
        }
    }

    override fun getCategories(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow {
            enrollmentDataSource.getCategory().data?.toCategoryList() ?: emptyList()
        }
    }

}
