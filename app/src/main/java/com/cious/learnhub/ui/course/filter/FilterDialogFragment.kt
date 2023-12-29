package com.cious.learnhub.ui.course.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.cious.learnhub.R
import com.cious.learnhub.databinding.FragmentFilterDialogBinding
import com.cious.learnhub.model.Category
import com.cious.learnhub.ui.course.CourseViewModel
import com.cious.learnhub.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.getViewModel


class FilterDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentFilterDialogBinding

    private val viewModel: CourseViewModel by lazy {
        requireParentFragment().getViewModel()
    }

    private val adapter: CategoryListFilterAdapter by lazy {
        CategoryListFilterAdapter(object : CategoryListener{
            override fun onCategoryChecked(category: Category) {
                viewModel.addSelectedCategory(category)
            }

            override fun onCategoryUnchecked(category: Category) {
                viewModel.removeSelectedCategory(category)
            }

            override fun getSelectedCategories(): List<Category>? {
                return viewModel.selectedCategories.value
            }
        })
    }


    private var isLevelBeginnerChecked : Boolean = false
    private var isLevelIntermediateChecked : Boolean = false
    private var isLevelAdvanceChecked : Boolean = false
    private var filterListener: onFilterListener? = null

    interface onFilterListener{
        fun filterApplied (category: List<Category>?, level: String? )
    }


    fun applyFilterListener(listener: onFilterListener){
        filterListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterDialogBinding.inflate(layoutInflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeCategoryList()
        setOnClickListener()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )


        dialog?.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
    }

    private fun setOnClickListener() {
        binding.btnApplyFilter.setOnClickListener{
            applyFilter()
            dismiss()
        }
    }

    private fun applyFilter() {
        val category = viewModel.selectedCategories.value
        val level = when(binding.rgLevel.checkedRadioButtonId){
            R.id.radio_beginner -> "Beginner"
            R.id.radio_intermediate -> "Intermediate"
            R.id.radio_advance -> "Advance"
            else -> null
        }
        filterListener?.filterApplied(category = category, level = level)
    }

    private fun observeCategoryList() {
        viewModel.categories.observe(viewLifecycleOwner){
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let {
                        adapter.submitData(it)
                    }
                }
            )
        }
    }

    private fun setupRecyclerView() {
        binding.rvCategoryList.adapter = adapter
    }

}