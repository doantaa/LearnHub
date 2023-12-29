package com.cious.learnhub.ui.course.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cious.learnhub.databinding.LayoutItemCategoryFilterBinding
import com.cious.learnhub.model.Category

class CategoryListFilterAdapter(
    private val checkListener: CategoryListener
): RecyclerView.Adapter<CategoryListFilterViewHolder>() {


    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryListFilterViewHolder {
        val binding = LayoutItemCategoryFilterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return CategoryListFilterViewHolder(binding,checkListener)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CategoryListFilterViewHolder, position: Int) {
        val category = differ.currentList[position]
        holder.bind(category)
    }

    fun submitData(categoryList: List<Category>){
        differ.submitList(categoryList)
    }

}

interface CategoryListener{
    fun onCategoryChecked(category: Category)
    fun onCategoryUnchecked(category: Category)

    fun getSelectedCategories(): List<Category>?
}