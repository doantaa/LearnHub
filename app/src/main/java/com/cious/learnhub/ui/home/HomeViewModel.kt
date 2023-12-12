package com.cious.learnhub.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cious.learnhub.data.repository.CourseRepository
import com.cious.learnhub.model.Course
import com.cious.learnhub.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: CourseRepository
) : ViewModel() {
    private val _courses = MutableLiveData<ResultWrapper<List<Course>>>()
    val courses: LiveData<ResultWrapper<List<Course>>>
        get() = _courses

    val categories = repository.getCategories().asLiveData(Dispatchers.IO)

    fun getCourses(
        category: String? = null, title: String? = null, level: String? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCourses(
                if (category == "C-0ALL") null else category, title, level
            ).collect() {
                _courses.postValue(it)
            }
        }
    }
}