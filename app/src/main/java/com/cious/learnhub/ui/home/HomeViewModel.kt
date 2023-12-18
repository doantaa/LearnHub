package com.cious.learnhub.ui.home

import android.icu.text.CaseMap.Title
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cious.learnhub.data.repository.CourseRepository
import com.cious.learnhub.model.Category
import com.cious.learnhub.model.Course
import com.cious.learnhub.utils.ResultWrapper
import com.cious.learnhub.utils.proceedFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class HomeViewModel(
    private val repository: CourseRepository
) : ViewModel() {
    private val _courses = MutableLiveData<ResultWrapper<List<Course>>>()
    val courses: LiveData<ResultWrapper<List<Course>>>
        get() = _courses

    val categories = repository.getCategories().asLiveData(Dispatchers.IO)

    fun getCourses(
        category: String? = null,
        title: String? = null,
        level: String? = null
    ){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCourses(category,title, level).collect() {
                _courses.postValue(it)
            }
        }
    }
}