package com.cious.learnhub.ui.myclass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cious.learnhub.data.repository.CourseRepository
import com.cious.learnhub.data.repository.EnrollmentRepository
import com.cious.learnhub.model.Course
import com.cious.learnhub.model.Enrollment
import com.cious.learnhub.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyClassViewModel(private val repository: EnrollmentRepository) : ViewModel() {
    private val _enrollment = MutableLiveData<ResultWrapper<List<Enrollment>>>()
    val courses: LiveData<ResultWrapper<List<Enrollment>>>
        get() = _enrollment

    fun getCourses(
        category: String? = null,
        title: String? = null,
        level: String? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getEnrollment(category, title, level).collect {
                _enrollment.postValue(it)
            }
        }
    }
}