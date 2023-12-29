package com.cious.learnhub.ui.course

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.cious.learnhub.R
import com.cious.learnhub.databinding.FragmentCourseBinding
import com.cious.learnhub.model.Category
import com.cious.learnhub.ui.course.filter.FilterDialogFragment
import com.cious.learnhub.ui.detail.CourseDetailActivity
import com.cious.learnhub.utils.hideKeyboard
import com.cious.learnhub.utils.proceedWhen
import com.google.android.material.chip.Chip
import org.koin.androidx.viewmodel.ext.android.viewModel

class CourseFragment : Fragment(), FilterDialogFragment.onFilterListener {

    private lateinit var binding: FragmentCourseBinding
    private val filterDialogFragment : FilterDialogFragment by lazy { FilterDialogFragment() }

    private val courseListAdapter: CourseListAdapter by lazy {
        CourseListAdapter {
            Log.d("BEFORE", it.id.toString())
            CourseDetailActivity.startActivity(requireContext(), it.id)
        }
    }


    private val viewModel: CourseViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCourseBinding.inflate(inflater, container, false)
        return binding.root

    }

    private var selectedCategories: List<Category>? = null
    private var selectedLevel : String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        invokeCourseData()
        observeCourseData()
        setupCourseType()
        setupSearch()
        setFilterButtonListener()
    }

    private fun setFilterButtonListener() {
        binding.tvFilter.setOnClickListener{
            filterDialogFragment.applyFilterListener(this)
            filterDialogFragment.show(childFragmentManager, "Filter")
        }
    }

    private fun setupSearch() {
        binding.fieldSearch.etSearch.clearFocus()
        val keyword = binding.fieldSearch.etSearch.text
        binding.fieldSearch.btnSearch.setOnClickListener {
            Toast.makeText(requireContext(), keyword, Toast.LENGTH_SHORT).show()
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
        binding.chipGroupMain.setOnCheckedStateChangeListener { group, checkedId ->

            if(checkedId != emptyList<Int>()){
                val chip: Chip? = group.findViewById(checkedId[0])
                chip?.let {
                    viewModel.getCourses(courseType = it.text.toString())
                }
            } else {
                binding.chipAll.isChecked = true
            }
        }


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

    override fun filterApplied(category: List<Category>?, level: String?) {
        selectedCategories = category
        val idCategory = category?.map { it.id }
        selectedLevel = level

        viewModel.getCourses(category = idCategory, level = selectedLevel)
    }

}