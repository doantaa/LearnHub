package com.cious.learnhub.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cious.learnhub.data.repository.CourseRepository
import com.cious.learnhub.data.repository.EnrollmentRepository
import com.cious.learnhub.data.repository.ProfileRepository
import com.cious.learnhub.model.Category
import com.cious.learnhub.model.Course
import com.cious.learnhub.model.ProfileModel
import com.cious.learnhub.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val courseRepository: CourseRepository,
    private val userRepository: ProfileRepository,
    private val enrollmentRepository: EnrollmentRepository

) : ViewModel() {
    private val _courses = MutableLiveData<ResultWrapper<List<Course>>>()
    val courses: LiveData<ResultWrapper<List<Course>>>
        get() = _courses

    private val _categories = MutableLiveData<ResultWrapper<List<Category>>>()


    private val _userData = MutableLiveData<ResultWrapper<ProfileModel>>()
    val userData : LiveData<ResultWrapper<ProfileModel>>
        get() = _userData

    val categories: LiveData<ResultWrapper<List<Category>>>
        get() = _categories

    val enrollment = enrollmentRepository.getEnrollment().asLiveData(Dispatchers.IO)



    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            courseRepository.getCategories().collect() { _categories.postValue(it) }
        }
    }

    fun getCourses(
        category: String? = null, title: String? = null, level: String? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            courseRepository.getCourses(
                if (category == "C-0ALL") null else category, title, level
            ).collect() {
                _courses.postValue(it)
            }
        }
    }

    fun getUserProfile(){
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getProfile().collect(){
                _userData.postValue(it)
            }
        }
    }
}