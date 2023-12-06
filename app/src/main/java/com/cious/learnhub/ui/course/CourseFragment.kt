package com.cious.learnhub.ui.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cious.learnhub.data.network.api.datasource.CourseApiDataSouce
import com.cious.learnhub.data.network.api.service.CourseService
import com.cious.learnhub.data.repository.CourseRepositoryImpl
import com.cious.learnhub.databinding.FragmentCourseBinding
import com.cious.learnhub.utils.GenericViewModelFactory
import com.cious.learnhub.utils.proceedWhen

class CourseFragment : Fragment() {

    private lateinit var binding: FragmentCourseBinding

    private val courseListAdapter: CourseListAdapter by lazy {
        CourseListAdapter {

        }
    }

    private val viewModel: CourseViewModel by viewModels {
        val service = CourseService.invoke(ChuckerInterceptor(requireContext()))
        val dataSource = CourseApiDataSouce(service)
        val repository = CourseRepositoryImpl(dataSource)
        GenericViewModelFactory.create(CourseViewModel(repository))
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCourseBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        invokeCourseData()
        observeCourseData()
    }

    private fun observeCourseData() {
        viewModel.courses.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = {
                binding.rvCourse.isVisible = true
                it.payload?.let {
                    courseListAdapter.setData(it)
                }
            }, doOnLoading = {}, doOnEmpty = {}, doOnError = {})
        }
    }

    private fun invokeCourseData() {
        viewModel.getCourses(null, null, null)
    }

    private fun setupRecyclerView() {
        binding.rvCourse.adapter = courseListAdapter
    }
}