package com.cious.learnhub.ui.course

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cious.learnhub.data.repository.CourseRepository
import com.cious.learnhub.model.Category
import com.cious.learnhub.model.Course
import com.cious.learnhub.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CourseViewModel(
    private val repository: CourseRepository
) : ViewModel() {
    private val _courses = MutableLiveData<ResultWrapper<List<Course>>>()
    val courses: LiveData<ResultWrapper<List<Course>>>
        get() = _courses

    private val _selectedCategories = MutableLiveData<List<Category>>()

    val selectedCategories: LiveData<List<Category>?>
        get() = _selectedCategories


    val categories = repository.getCategories().asLiveData(Dispatchers.IO)
    fun getCourses(
        category: List<String>? = null, title: String? = null, courseType: String? = null, level: String? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCourses(
                category = category,
                title = title,
                level = level,
                courseType = if(courseType == "All") null else courseType
                ).collect() {
                _courses.postValue(it)
            }
        }
    }

    fun addSelectedCategory(category: Category){
        _selectedCategories.value = (_selectedCategories.value.orEmpty() + category).distinct()
    }

    fun removeSelectedCategory(category: Category){
        _selectedCategories.value = (_selectedCategories.value.orEmpty() - category).distinct()
    }

}