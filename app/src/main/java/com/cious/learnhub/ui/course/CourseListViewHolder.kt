package com.cious.learnhub.ui.course

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cious.learnhub.core.ViewHolderBinder
import com.cious.learnhub.databinding.ItemCourseBinding
import com.cious.learnhub.databinding.ItemCourseLinearBinding
import com.cious.learnhub.model.Course
import com.cious.learnhub.utils.toCurrencyFormat

class CourseListViewHolder(
    private val binding: ItemCourseLinearBinding, val onItemClick: (Course) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Course> {
    override fun bind(item: Course) {
        with(item) {
            binding.ivCourseImage.load("https://raw.githubusercontent.com/doantaa/BinarFoodApp-Resource/main/nasi_campur.png")
            binding.tvCourseCategory.text = item.categoryId
            binding.tvCourseTitle.text = item.title
            binding.tvRating.text = item.rating.toString()
            binding.tvCourseInstructor.text = item.instructor
            binding.tvLevel.text = item.level
            binding.tvPrice.text = item.price.toCurrencyFormat()
        }
    }

}