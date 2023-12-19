package com.cious.learnhub.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cious.learnhub.core.ViewHolderBinder
import com.cious.learnhub.databinding.ItemCourseBinding
import com.cious.learnhub.model.Course
import com.cious.learnhub.utils.toCurrencyFormat

class HomeCourseListViewHolder(
    private val binding: ItemCourseBinding, val onItemClick: (Course) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Course> {
    override fun bind(item: Course) {
        with(item) {
            binding.root.setOnClickListener {
                onItemClick.invoke(item)
            }
            binding.ivCourseImage.load(item.imageUrl){
                crossfade(true)
            }
            binding.tvCourseCategory.text = item.categoryName
            binding.tvCourseTitle.text = item.title
            binding.tvRating.text = item.rating.toString()
            binding.tvCourseInstructor.text = item.instructor
            binding.tvLevel.text = item.level
            binding.tvModuleCount.text = buildString {
                append(item.moduleCount)
                append(" Modul")
            }
            binding.tvTotalDuration.text = buildString {
                append(item.totalDuration)
                append(" Menit")
            }
            binding.tvLevel.text = item.level
            binding.tvPrice.text = if (item.price == 0) "GRATIS" else item.price.toCurrencyFormat()
        }
    }

}