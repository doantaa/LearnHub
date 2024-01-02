package com.cious.learnhub.ui.home.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.cious.learnhub.R
import com.cious.learnhub.databinding.ActivityHomeSearchBinding
import com.cious.learnhub.ui.course.CourseListAdapter
import com.cious.learnhub.ui.detail.CourseDetailActivity
import com.cious.learnhub.utils.hideKeyboard
import com.cious.learnhub.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeSearchActivity : AppCompatActivity() {
    private val binding: ActivityHomeSearchBinding by lazy {
        ActivityHomeSearchBinding.inflate(layoutInflater)
    }

    private val viewModel: HomeSearchViewModel by viewModel {
        parametersOf(intent?.extras)
    }


    private val courseListAdapter : CourseListAdapter by lazy {
        CourseListAdapter {
            CourseDetailActivity.startActivity(this, it.id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupRecyclerView()
        invokeCourseData()
        observeCourseData()
        setupSearch()
    }

    private fun setupSearch() {
        binding.fieldSearch.etSearch.setText(viewModel.title)
        val keyword = binding.fieldSearch.etSearch.text
        binding.fieldSearch.btnSearch.setOnClickListener {
            viewModel.getCourse(title = keyword.toString())
            binding.fieldSearch.etSearch.clearFocus()
            hideKeyboard(it)
        }
        binding.fieldSearch.etSearch.setOnEditorActionListener { textView, i, keyEvent ->
            viewModel.getCourse(title = keyword.toString())
            binding.fieldSearch.etSearch.clearFocus()
            hideKeyboard(textView)
            return@setOnEditorActionListener true
        }
    }

    private fun observeCourseData() {
        viewModel.course.observe(this) {
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
        viewModel.getCourse(viewModel.title)
    }

    private fun setupRecyclerView() {
        binding.rvCourse.adapter = courseListAdapter
    }

    companion object {
        const val EXTRA_TITLE = "EXTRA_TITLE"
        fun startActivity(context: Context, title: String) {
            val intent = Intent(context, HomeSearchActivity::class.java)
            intent.putExtra(EXTRA_TITLE, title)
            context.startActivity(intent)
        }
    }
}