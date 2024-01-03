package com.cious.learnhub.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import com.cious.learnhub.R
import com.cious.learnhub.databinding.FragmentHomeBinding
import com.cious.learnhub.model.Category
import com.cious.learnhub.ui.detail.CourseDetailActivity
import com.cious.learnhub.ui.home.adapter.HomeCourseListAdapter
import com.cious.learnhub.ui.home.search.HomeSearchActivity
import com.cious.learnhub.utils.hideKeyboard
import com.cious.learnhub.utils.proceedWhen
import com.google.android.material.chip.Chip
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomeBinding

    private val homeCourseListAdapter: HomeCourseListAdapter by lazy {
        HomeCourseListAdapter {
            CourseDetailActivity.startActivity(requireContext(), it.id)
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
        setupSearchBar()
    }


    override fun onResume() {
        super.onResume()
        binding.search.etSearch.clearFocus()
        binding.chipGroupCategory.check(0 + 1)
    }


    private fun setupSearchBar() {
        binding.search.etSearch.clearFocus()
        val keyword = binding.search.etSearch.text
        binding.search.btnSearch.setOnClickListener {
            viewModel.getCourses(title = keyword.toString())
            binding.search.root.clearFocus()
            hideKeyboard()
            HomeSearchActivity.startActivity(requireContext(), keyword.toString())

        }
        binding.search.etSearch.setOnFocusChangeListener { view, boolean ->
            if (boolean) {
                HomeSearchActivity.startActivity(requireContext(), keyword.toString())
                hideKeyboard()
            }
        }
        binding.search.etSearch.setOnEditorActionListener { textView, i, keyEvent ->
            binding.search.root.clearFocus()
            hideKeyboard()
            return@setOnEditorActionListener true
        }
    }

    private fun observeData() {
        observeCategoryData()
        observeCourseData()
        observeUserData()
        observeLastEnrollCourse()
    }

    private fun observeLastEnrollCourse() {
        viewModel.enrollment.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.get(0).apply {
                        binding.shimmerHomeLastCourse.isVisible = false
                        binding.clLastCourseItem.isVisible = true
                        binding.tvLastCourseItemTitle.text = this?.title
                        binding.tvLastCourseItemSubtitle.text = this?.categoryName
                        binding.ivLastCourseItemImage.load(this?.imageUrl)
                        Log.d("USER ENROLL SUCCESS", this.toString())
                        binding.llLastCourse.setOnClickListener {
                            CourseDetailActivity.startActivity(requireContext(), this?.id ?: 1)
                        }
                    }
                },
                doOnLoading = {
                    binding.shimmerHomeLastCourse.isVisible = true
                    binding.clLastCourseItem.isVisible = false
                },
                doOnEmpty = {
                    binding.clLastCourseItem.isVisible = false
                    binding.clEmptyCourse.isVisible = true
                    binding.shimmerHomeLastCourse.isVisible = false
                },
                doOnError = {
                    binding.clLastCourseItem.isVisible = false
                    binding.clEmptyCourse.isVisible = true
                    binding.shimmerHomeLastCourse.isVisible = false
                    Log.d("ERROR", it.message.toString())
                }
            )
        }


    }

    private fun observeUserData() {

        viewModel.userData.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let {
                        binding.ivProfile.load(it.profileUrl)
                        val name = it.name
                        binding.tvGreetingTitle.text = getString(R.string.hello, name)
                    }
                },
                doOnError = {
                    binding.ivProfile.setImageResource(R.drawable.ic_non_login_profile)
                    binding.tvGreetingTitle.text = getString(R.string.hello_learner)
                }
            )
        }
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
                binding.chipGroupCategory.removeAllViews()
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
                checkFirstChip()


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


            })
        }
    }

    private fun checkFirstChip() {
        val firstChip = binding.chipGroupCategory.children.toList().get(0)
        binding.chipGroupCategory.check( firstChip.id)
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
            if (group.checkedChipId > category.size) {
                val selectedChip = binding.chipGroupCategory.children.toList()
                    .mapIndexed { index, view ->
                        Pair(index, view)
                    }
                    .filter {
                        (it.second as Chip).isChecked
                    }
                val categoryId = category[selectedChip[0].first].id
                viewModel.getCourses(category = categoryId)
                Log.d("CATEGORY", categoryId)

            } else if (checkedId != emptyList<Int>() && group.checkedChipId < category.size) {
                val id = category[checkedId[0] - 1].id
                viewModel.getCourses(category = id)
            } else {
                checkFirstChip()
            }
        }

    }

    private fun invokeData() {
        viewModel.getCourses()
        viewModel.getCategories()
        viewModel.getUserProfile()
    }

    private fun setupRecyclerView() {
        binding.rvCourseList.adapter = homeCourseListAdapter

    }


}