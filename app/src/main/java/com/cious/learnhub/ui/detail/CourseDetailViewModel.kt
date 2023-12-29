package com.cious.learnhub.ui.detail

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cious.learnhub.data.repository.EnrollmentRepository
import com.cious.learnhub.model.Course
import com.cious.learnhub.model.Enrollment
import com.cious.learnhub.model.Progress
import com.cious.learnhub.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CourseDetailViewModel(extras: Bundle?, private val repository: EnrollmentRepository) : ViewModel() {
    val courseId = extras?.getInt("EXTRA_ID")

    private val _enrollment = MutableLiveData<ResultWrapper<Enrollment>>()
    val enrollment: LiveData<ResultWrapper<Enrollment>>
        get() = _enrollment

    private val _course = MutableLiveData<ResultWrapper<Course>>()
    val course: LiveData<ResultWrapper<Course>>
        get() = _course

    private val _progress = MutableLiveData<ResultWrapper<Progress>>()
    val progress : LiveData<ResultWrapper<Progress>>
        get() = _progress

    fun getCourseById(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCoursesById(id).collect(){
                _enrollment.postValue(it)
            }
        }
    }

    fun postProgress(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.postProgress(id)
                .collect{
                    _progress.postValue(it)
                }
        }
    }

    private val _videoUrl = MutableLiveData<String>()
    val videoUrl: LiveData<String>
        get() = _videoUrl
    fun getVideoUrl(videoUrl:String) {
        _videoUrl.postValue(videoUrl)
    }
}