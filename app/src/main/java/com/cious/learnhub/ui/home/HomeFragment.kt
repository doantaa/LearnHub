package com.cious.learnhub.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import com.cious.learnhub.R
import com.cious.learnhub.databinding.FragmentHomeBinding
import com.cious.learnhub.model.Category
import com.cious.learnhub.ui.home.adapter.HomeCourseListAdapter
import com.cious.learnhub.utils.hideKeyboard
import com.cious.learnhub.utils.proceedWhen
import com.google.android.material.chip.Chip
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomeBinding

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
        binding.search.root.clearFocus()
        val keyword = binding.search.etSearch.text
        binding.search.btnSearch.setOnClickListener {
            viewModel.getCourses(title = keyword.toString())
            binding.search.root.clearFocus()
            hideKeyboard()
        }
        binding.search.etSearch.setOnEditorActionListener { textView, i, keyEvent ->
            viewModel.getCourses(title = keyword.toString())
            binding.search.root.clearFocus()
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
                binding.chipGroupCategory.isVisible = true
                binding.shimmerHomeCategory.isVisible = false
                data.payload?.map { category ->
                    binding.chipGroupCategory.addView(
                        createCategoryChip(
                            requireContext(),
                            category
                        )
                    )

                }
                data.payload?.let { categories ->
                    setCategoryListener(categories)
                }


            }, doOnLoading = {
                binding.chipGroupCategory.isVisible = false
                binding.shimmerHomeCategory.isVisible = true

            }, doOnEmpty = {
                binding.chipGroupCategory.isVisible = false
                binding.shimmerHomeCategory.isVisible = false
                binding.shimmerHomeCategory.isVisible = false


            }, doOnError = {
                binding.chipGroupCategory.isVisible = false
                binding.shimmerHomeCategory.isVisible = false
                binding.shimmerHomeCategory.isVisible = false
                Log.d("CATEGORY", it.message.toString())


            })
        }
    }



    private fun createCategoryChip(context: Context, category: Category): Chip {
        return Chip(context).apply {
            text = category.name
            isCheckedIconVisible = false
            setChipBackgroundColorResource(R.color.chip_background_color)
            setTextAppearance(R.style.ChipTextStyle)
            isCheckable = true
        }

    }

    private fun setCategoryListener(category: List<Category>) {
        binding.chipGroupCategory.setOnCheckedStateChangeListener { group, checkedId ->
            Log.d("CLICKED", checkedId.toString())
            if(checkedId != emptyList<Int>()){
                val id = category[checkedId[0] - 1].id
                viewModel.getCourses(category = id)
            } else {
                binding.chipGroupCategory.check(0 + 1)
            }
        }

    }
    private fun invokeData() {
        viewModel.getCourses()
    }

    private fun setupRecyclerView() {
        binding.rvCourseList.adapter = homeCourseListAdapter

    }


}