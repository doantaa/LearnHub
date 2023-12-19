package com.cious.learnhub.ui.detail.pagerfragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.cious.learnhub.R
import com.cious.learnhub.databinding.FragmentAboutBinding
import com.cious.learnhub.ui.detail.CourseDetailActivity
import com.cious.learnhub.ui.detail.CourseDetailViewModel
import com.cious.learnhub.utils.proceedWhen

class AboutFragment : Fragment() {

    private val viewModel: CourseDetailViewModel by activityViewModels()

    private lateinit var binding: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        invokeData()
//        observeData()
    }

    private fun invokeData() {
        val id = viewModel.courseId ?: 0
        viewModel.getCourseById(id)
    }

    private fun observeData() {
        viewModel.courses.observe(viewLifecycleOwner){
            it.proceedWhen (
                doOnSuccess = {
                    it.payload.let {
                        binding.tvAbout.text = it?.about
                        binding.tvObjective.text = it?.objective
                    }
                }
            )
        }
    }
}