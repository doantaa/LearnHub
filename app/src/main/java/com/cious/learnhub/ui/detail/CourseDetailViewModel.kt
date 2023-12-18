package com.cious.learnhub.ui.detail

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cious.learnhub.data.repository.CourseRepository
import com.cious.learnhub.data.repository.EnrollmentRepository
import com.cious.learnhub.model.Course
import com.cious.learnhub.model.Enrollment
import com.cious.learnhub.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CourseDetailViewModel(extras: Bundle?, private val repository: CourseRepository) : ViewModel() {
    val courseId = extras?.getInt("EXTRA_ID")

    private val _courses = MutableLiveData<ResultWrapper<Course>>()
    val courses: LiveData<ResultWrapper<Course>>
        get() = _courses

    fun getCourseById(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCoursesById(id).collect(){
                _courses.postValue(it)
            }
        }
    }
}