package com.cious.learnhub.ui.course

import android.icu.text.CaseMap.Title
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cious.learnhub.data.repository.CourseRepository
import com.cious.learnhub.model.Category
import com.cious.learnhub.model.Course
import com.cious.learnhub.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CourseViewModel(
    private val repository: CourseRepository
) : ViewModel() {
    private val _courses = MutableLiveData<ResultWrapper<List<Course>>>()
    val courses: LiveData<ResultWrapper<List<Course>>>
        get() = _courses

    fun getCourses(
        category: String?,
        title: String?,
        level: String?
    ){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCourses(category, title, level).collect(){
                _courses.postValue(it)
            }
        }
    }
}