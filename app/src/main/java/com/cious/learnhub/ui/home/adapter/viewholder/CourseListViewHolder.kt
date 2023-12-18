package com.cious.learnhub.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.cious.learnhub.core.ViewHolderBinder
import com.cious.learnhub.databinding.ItemCourseBinding
import com.cious.learnhub.model.Course
import com.cious.learnhub.utils.toCurrencyFormat

class CourseListViewHolder(
    private val binding: ItemCourseBinding, val onItemClick: (Course) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Course> {
    override fun bind(item: Course) {
        with(item) {
            binding.tvCourseCategory.text = item.categoryId
            binding.tvCourseTitle.text = item.title
            binding.tvRating.text = item.rating.toString()
            binding.tvCourseInstructor.text = item.instructor
            binding.tvLevel.text = item.level
            binding.tvPrice.text = item.price.toCurrencyFormat()
        }
    }

}