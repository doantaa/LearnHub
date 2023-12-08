package com.cious.learnhub.ui.course

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cious.learnhub.core.ViewHolderBinder
import com.cious.learnhub.databinding.ItemCourseLinearBinding
import com.cious.learnhub.model.Course
import com.cious.learnhub.utils.toCurrencyFormat

class CourseListViewHolder(
    private val binding: ItemCourseLinearBinding, val onItemClick: (Course) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Course> {
    override fun bind(item: Course) {
        with(item) {
            binding.ivCourseImage.load(item.imageUrl)
            binding.tvCourseCategory.text = item.categoryName
            binding.tvCourseTitle.text = item.title
            binding.tvLevel.text = item.level
            binding.tvRating.text = item.rating.toString()
            binding.tvModuleCount.text = buildString {
                append(item.moduleCount)
                append(" Modul")
            }
            binding.tvTotalDuration.text = buildString {
                append(item.totalDuration)
                append(" Menit")
            }
            binding.tvCourseInstructor.text = item.instructor
            binding.tvPrice.text = item.price.toCurrencyFormat()
        }
    }

}