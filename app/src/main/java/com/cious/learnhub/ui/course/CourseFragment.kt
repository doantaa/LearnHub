package com.cious.learnhub.ui.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cious.learnhub.R
import com.cious.learnhub.data.network.api.datasource.CourseApiDataSource
import com.cious.learnhub.data.network.api.service.CourseService
import com.cious.learnhub.data.repository.CourseRepositoryImpl
import com.cious.learnhub.databinding.FragmentCourseBinding
import com.cious.learnhub.ui.detail.CourseDetailActivity
import com.cious.learnhub.utils.GenericViewModelFactory
import com.cious.learnhub.utils.hideKeyboard
import com.cious.learnhub.utils.proceedWhen

class CourseFragment : Fragment() {

    private lateinit var binding: FragmentCourseBinding

    private val courseListAdapter: CourseListAdapter by lazy {
        CourseListAdapter {
            CourseDetailActivity.startActivity(requireContext(), it)
        }
    }

    private val courseTypeListAdapter: CourseTypeListAdapter by lazy {
        CourseTypeListAdapter {
            viewModel.getCourses(courseType = it)
        }
    }

    private val viewModel: CourseViewModel by viewModels {
        val service = CourseService.invoke(ChuckerInterceptor(requireContext()))
        val dataSource = CourseApiDataSource(service)
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
        setupCourseType()
        setupSearch()
    }

    private fun setupSearch() {
        binding.fieldSearch.etSearch.clearFocus()
        val keyword = binding.fieldSearch.etSearch.text
        binding.fieldSearch.btnSearch.setOnClickListener {
            Toast.makeText(requireContext(), keyword, Toast.LENGTH_SHORT).show()
            viewModel.getCourses(title = keyword.toString())
            binding.fieldSearch.etSearch.clearFocus()
            hideKeyboard()
        }
        binding.fieldSearch.etSearch.setOnEditorActionListener { textView, i, keyEvent ->
            viewModel.getCourses(title = keyword.toString())
            binding.fieldSearch.etSearch.clearFocus()
            hideKeyboard()
            return@setOnEditorActionListener true
        }


    }

    private fun setupCourseType() {
        binding.rvCourseType.adapter = courseTypeListAdapter
        courseTypeListAdapter.setData(listOf("All", "Premium", "Free"))
    }

    private fun observeCourseData() {
        viewModel.courses.observe(viewLifecycleOwner) {
            it.proceedWhen(

                doOnSuccess = {
                    binding.rvCourse.isVisible = true
                    binding.layoutState.root.isVisible = false
                    binding.shimmerCourse.isVisible = false
                    it.payload?.let {
                        courseListAdapter.setData(it)
                    }
                },
                doOnLoading = {
                    binding.rvCourse.isVisible = false
                    binding.layoutState.root.isVisible = false
                    binding.shimmerCourse.isVisible = true
                },
                doOnEmpty = {
                    binding.rvCourse.isVisible = false
                    binding.shimmerCourse.isVisible = false
                    binding.layoutState.root.isVisible = true
                }, doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvEmptyTitle.text = getText(R.string.text_error)
                    binding.layoutState.tvEmptyDescription.text = it.exception?.message
                })
        }
    }

    private fun invokeCourseData() {
        viewModel.getCourses()
    }

    private fun setupRecyclerView() {
        binding.rvCourse.adapter = courseListAdapter
    }
}