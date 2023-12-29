package com.cious.learnhub.ui.course.filter

import androidx.recyclerview.widget.RecyclerView
import com.cious.learnhub.core.ViewHolderBinder
import com.cious.learnhub.databinding.LayoutItemCategoryFilterBinding
import com.cious.learnhub.model.Category

class CategoryListFilterViewHolder(
    private val binding: LayoutItemCategoryFilterBinding,
    private val checkListener: CategoryListener
): RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Category> {
    override fun bind(item: Category) {
        with(item){
            binding.checkboxName.isChecked = item in checkListener.getSelectedCategories().orEmpty()
            binding.checkboxName.text = item.name
            binding.checkboxName.setOnCheckedChangeListener{ _,isChecked ->
                if(isChecked){
                    checkListener.onCategoryChecked(item)
                } else {
                    checkListener.onCategoryUnchecked(item)
                }
            }
        }
    }


}