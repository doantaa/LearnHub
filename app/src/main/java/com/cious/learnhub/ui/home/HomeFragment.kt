package com.cious.learnhub.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cious.learnhub.R
import com.cious.learnhub.data.network.api.datasource.CourseApiDataSource
import com.cious.learnhub.data.network.api.service.CourseService
import com.cious.learnhub.data.repository.CourseRepositoryImpl
import com.cious.learnhub.databinding.FragmentHomeBinding
import com.cious.learnhub.ui.home.adapter.CategoryListAdapter
import com.cious.learnhub.ui.home.adapter.HomeCourseListAdapter
import com.cious.learnhub.utils.GenericViewModelFactory
import com.cious.learnhub.utils.hideKeyboard
import com.cious.learnhub.utils.proceedWhen

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels {
        val service = CourseService.invoke(ChuckerInterceptor(requireContext()))
        val dataSource = CourseApiDataSource(service)
        val repository = CourseRepositoryImpl(dataSource)
        GenericViewModelFactory.create(HomeViewModel(repository))
    }

    private val categoryListAdapter: CategoryListAdapter by lazy {
        CategoryListAdapter {
            viewModel.getCourses(category = it.id)
        }
    }

    private val homeCourseListAdapter: HomeCourseListAdapter by lazy {
        HomeCourseListAdapter {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        invokeData()
        observeData()
        setupProfileData()
        setupSearchBar()
    }

    private fun setupSearchBar() {
        binding.etSearch.clearFocus()
        val keyword = binding.etSearch.text
        binding.btnSearch.setOnClickListener {
            viewModel.getCourses(title = keyword.toString())
            binding.etSearch.clearFocus()
            hideKeyboard()
        }
        binding.etSearch.setOnEditorActionListener { textView, i, keyEvent ->
            viewModel.getCourses(title = keyword.toString())
            binding.etSearch.clearFocus()
            hideKeyboard()
            return@setOnEditorActionListener true
        }
    }

    private fun setupProfileData() {
        binding.ivProfile.load("https://pbs.twimg.com/profile_images/1663195620851716099/GbxCSqEZ_400x400.jpg")
        val name = "IU"
        binding.tvGreetingTitle.text = getString(R.string.hello, name)
    }

    private fun observeData() {
        observeCategoryData()
        observeCourseData()
    }

    private fun observeCourseData() {
        viewModel.courses.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = { data ->
                binding.rvCourseList.isVisible = true
                binding.shimmerHomeCourse.isVisible = false
                binding.layoutState.root.isVisible = false
                data.payload?.let {
                    homeCourseListAdapter.setData(it)
                }
                binding.rvCourseList.clearAnimation()
            }, doOnLoading = {
                binding.rvCourseList.isVisible = false
                binding.layoutState.root.isVisible = false
                binding.shimmerHomeCourse.isVisible = true

            }, doOnEmpty = {
                binding.layoutState.root.isVisible = true
                binding.shimmerHomeCourse.isVisible = false
                binding.rvCourseList.isVisible = false

            }, doOnError = {
                binding.layoutState.root.isVisible = true
                binding.shimmerHomeCourse.isVisible = false
                binding.rvCourseList.isVisible = false
                binding.layoutState.tvEmptyTitle.text = getString(R.string.text_error)
                binding.layoutState.tvEmptyDescription.text = it.exception?.message
            })
        }
    }

    private fun observeCategoryData() {
        viewModel.categories.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = { data ->
                binding.rvCourseCategory.isVisible = true
                binding.shimmerHomeCategory.isVisible = false
                data.payload?.let { categoryList ->
                    categoryListAdapter.setData(categoryList)
                }
                Log.d("CATEGORY", data.payload.toString())

            }, doOnLoading = {
                binding.rvCourseCategory.isVisible = false
                binding.shimmerHomeCategory.isVisible = true

            }, doOnEmpty = {
                binding.rvCourseCategory.isVisible = false
                binding.shimmerHomeCategory.isVisible = false

            }, doOnError = {


            })
        }
    }

    private fun invokeData() {
        viewModel.getCourses()
    }

    private fun setupRecyclerView() {
        setupCategoryRecyclerView()
        setupCourseRecyclerView()
    }

    private fun setupCourseRecyclerView() {
        binding.rvCourseList.adapter = homeCourseListAdapter
    }

    private fun setupCategoryRecyclerView() {
        binding.rvCourseCategory.adapter = categoryListAdapter
    }


}