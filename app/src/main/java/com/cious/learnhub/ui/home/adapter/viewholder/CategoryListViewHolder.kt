package com.cious.learnhub.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.cious.learnhub.core.ViewHolderBinder
import com.cious.learnhub.databinding.ItemCategoryBinding
import com.cious.learnhub.model.Category

class CategoryListViewHolder(
    private val binding: ItemCategoryBinding, val onItemClick: (Category) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Category> {
    override fun bind(item: Category) {
        with(item) {
            binding.tvCategoryName.text = item.name
        }
    }
}