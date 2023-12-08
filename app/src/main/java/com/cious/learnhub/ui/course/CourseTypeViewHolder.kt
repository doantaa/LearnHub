package com.cious.learnhub.ui.course

import androidx.recyclerview.widget.RecyclerView
import com.cious.learnhub.core.ViewHolderBinder
import com.cious.learnhub.databinding.ItemCategoryBinding

class CourseTypeViewHolder(
    private val binding: ItemCategoryBinding, val onItemClick: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<String> {
    override fun bind(item: String) {
        with(item) {
            binding.root.setOnClickListener {
                onItemClick.invoke(item)
            }
            binding.tvCategoryName.text = item
        }
    }

}