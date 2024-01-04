package com.cious.learnhub.ui.myclass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cious.learnhub.data.repository.AuthRepository
import com.cious.learnhub.data.repository.EnrollmentRepository
import com.cious.learnhub.model.Enrollment
import com.cious.learnhub.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyClassViewModel(
    private val repository: EnrollmentRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _enrollment = MutableLiveData<ResultWrapper<List<Enrollment>>>()
    val enrollment: LiveData<ResultWrapper<List<Enrollment>>>
        get() = _enrollment

    val categories = repository.getCategories().asLiveData(Dispatchers.IO)

    val isLogin = authRepository.isLogin()
    fun getCourses(
        category: String? = null,
        title: String? = null,
        courseType: String? = null,
        level: String? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getEnrollment(category, title, courseType, level).collect {
                _enrollment.postValue(it)
            }
        }
    }
}