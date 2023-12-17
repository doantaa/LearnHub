package com.cious.learnhub.ui.detail

import android.os.Bundle
import androidx.lifecycle.ViewModel

class CourseDetailViewModel(extras: Bundle?) : ViewModel() {
    val courseId = extras?.getInt("EXTRA_ID")

}