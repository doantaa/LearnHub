package com.cious.learnhub.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cious.learnhub.core.ViewHolderBinder
import com.cious.learnhub.databinding.ItemCategoryBinding
import com.cious.learnhub.model.Category
import com.cious.learnhub.ui.home.adapter.viewholder.CategoryListViewHolder

class CategoryListAdapter(
    private val onItemClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryListViewHolder>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {
        return CategoryListViewHolder(
            binding = ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClick
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CategoryListViewHolder, position: Int) {
        (holder as ViewHolderBinder<Category>).bind(differ.currentList[position])
    }

    fun setData(categoryList: List<Category>) {
        differ.submitList(categoryList)

    }

}