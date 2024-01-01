package com.cious.learnhub.ui.myclass

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.cious.learnhub.R
import com.cious.learnhub.databinding.FragmentMyClassBinding
import com.cious.learnhub.ui.authentication.login.LoginActivity
import com.cious.learnhub.ui.authentication.register.RegisterActivity
import com.cious.learnhub.ui.detail.CourseDetailActivity
import com.cious.learnhub.ui.home.search.HomeSearchActivity
import com.cious.learnhub.ui.myclass.adapter.CategoryMyClassAdapter
import com.cious.learnhub.ui.myclass.adapter.ProgressiveCourseAdapter
import com.cious.learnhub.utils.hideKeyboard
import com.cious.learnhub.utils.highLightWord
import com.cious.learnhub.utils.proceedWhen
import com.google.android.material.chip.Chip
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

class MyClassFragment : Fragment() {

    private lateinit var binding: FragmentMyClassBinding


    private val viewModel: MyClassViewModel by viewModel()

    private val progressiveCourseAdapter: ProgressiveCourseAdapter by lazy {
        ProgressiveCourseAdapter {
            CourseDetailActivity.startActivity(requireContext(), it.id)
        }
    }

    private val categoryListAdapter: CategoryMyClassAdapter by lazy {
        CategoryMyClassAdapter {
            viewModel.getCourses(category = it.id)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyClassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeData()
        checkTokenUser()
        setupSearch()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.incUserNotLogin.btnLogin.setOnClickListener {
            navigateToLogin()
        }
        binding.incUserNotLogin.tvIntentRegister.highLightWord(getString(R.string.text_highlight_register)) {
            navigateToRegister()
        }
    }

    private fun setupSearch() {
        binding.search.etSearch.clearFocus()
        val keyword = binding.search.etSearch.text
        binding.search.btnSearch.setOnClickListener {
            Toast.makeText(requireContext(), keyword, Toast.LENGTH_SHORT).show()
            binding.search.etSearch.clearFocus()
            hideKeyboard()
        }
        binding.search.etSearch.setOnEditorActionListener {
            _, _, _ ->
            viewModel.getCourses(title = keyword.toString())
            binding.search.etSearch.clearFocus()
            hideKeyboard()
            return@setOnEditorActionListener true
        }
    }

    private fun navigateToRegister() {
        startActivity(Intent(requireContext(), RegisterActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun navigateToLogin() {
        startActivity(Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun checkTokenUser() {
        if (!viewModel.isLogin) {
            binding.clUserNotLogin.isVisible = true
            binding.nsUserLogin.isVisible = false
        } else {
            binding.nsUserLogin.isVisible = true
            binding.clUserNotLogin.isVisible = false
        }
    }

    private fun observeData() {
        observeCourseData()
        observeCategoryData()
    }

    private fun observeCourseData() {
        viewModel.courses.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = {
                binding.shimmerMyClass.isVisible = false
                binding.rvClass.apply {
                    isVisible = true
                    adapter = progressiveCourseAdapter
                }
                it.payload?.let { data ->
                    progressiveCourseAdapter.setData(data)
                }
                binding.rvClass.clearAnimation()
            }, doOnLoading = {
                binding.rvClass.isVisible = false
                binding.layoutState.root.isVisible = false
                binding.shimmerMyClass.isVisible = true
            }, doOnEmpty = {
                binding.layoutState.root.isVisible = true
                binding.shimmerMyClass.isVisible = false
                binding.rvClass.isVisible = false
            }, doOnError = {
                binding.layoutState.root.isVisible = true
                binding.shimmerMyClass.isVisible = false
                binding.rvClass.isVisible = false
                binding.layoutState.tvEmptyTitle.text = getString(R.string.text_error)
                binding.layoutState.tvEmptyDescription.text = it.message
            }
            )
        }
    }

    private fun observeCategoryData() {
        viewModel.categories.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = { data ->
                binding.rvCategoryClass.isVisible = true
                binding.shimmerMyClassCategories.isVisible = false
                data.payload?.let { categoryList ->
                    categoryListAdapter.setData(categoryList)
                }
                binding.rvCategoryClass.clearAnimation()
            }, doOnLoading = {
                binding.rvCategoryClass.isVisible = false
                binding.shimmerMyClassCategories.isVisible = true

            }, doOnEmpty = {
                binding.rvCategoryClass.isVisible = false
                binding.shimmerMyClassCategories.isVisible = false


            }, doOnError = {
                binding.rvCategoryClass.isVisible = false
                binding.shimmerMyClassCategories.isVisible = false
            })
        }
    }

    private fun getData() {
        viewModel.getCourses()
    }

    private fun setupRecyclerView() {
        setupCategoryRecyclerView()
        setupCourseRecyclerView()
    }

    private fun setupCourseRecyclerView() {
        binding.rvClass.adapter = progressiveCourseAdapter
    }

    private fun setupCategoryRecyclerView() {
        binding.rvCategoryClass.adapter = categoryListAdapter
    }
}