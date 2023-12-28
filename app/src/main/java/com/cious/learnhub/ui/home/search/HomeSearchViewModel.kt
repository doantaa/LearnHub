package com.cious.learnhub.ui.home.search

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cious.learnhub.data.repository.CourseRepository
import com.cious.learnhub.model.Course
import com.cious.learnhub.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeSearchViewModel(
    extras: Bundle?,
    private val repository: CourseRepository
): ViewModel() {

    val title = extras?.getString("EXTRA_TITLE")

    val _course = MutableLiveData<ResultWrapper<List<Course>>>()
    val course: LiveData<ResultWrapper<List<Course>>>
        get() = _course
    fun getCourse(
        category: String? = null ,title: String? = null
    ){
        viewModelScope.launch(Dispatchers.IO) {

            repository.getCourses(
                category = category,
                title = title
            ).collect(){
                _course.postValue(it)
            }
        }
    }
}