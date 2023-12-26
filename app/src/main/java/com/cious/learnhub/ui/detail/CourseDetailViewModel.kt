package com.cious.learnhub.ui.detail

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

class CourseDetailViewModel(extras: Bundle?, private val repository: CourseRepository) : ViewModel() {
    val courseId = extras?.getInt("EXTRA_ID")

    private val _detailCourse = MutableLiveData<ResultWrapper<Course>>()
    val detailCourse: LiveData<ResultWrapper<Course>>
        get() = _detailCourse

    fun getCourseById(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCoursesById(id).collect(){
                _detailCourse.postValue(it)
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