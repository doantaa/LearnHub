package com.cious.learnhub.ui.myclass.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cious.learnhub.core.ViewHolderBinder
import com.cious.learnhub.databinding.ItemMyClassCategoriesBinding
import com.cious.learnhub.model.Category

class CategoryMyClassAdapter(
    private val onItemClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryMyClassListViewHolder>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    })


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMyClassListViewHolder {
        return CategoryMyClassListViewHolder(
            binding = ItemMyClassCategoriesBinding.inflate(
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

    override fun onBindViewHolder(holder: CategoryMyClassListViewHolder, position: Int) {
        (holder as ViewHolderBinder<Category>).bind(differ.currentList[position])
    }

    fun setData(categoryList: List<Category>) {
        differ.submitList(categoryList)
        Log.d("DIFFER SIZE", categoryList.toString())
    }

}

class CategoryMyClassListViewHolder(
    private val binding: ItemMyClassCategoriesBinding, val onItemClick: (Category) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Category> {
    override fun bind(item: Category) {
        with(item) {
            binding.root.setOnClickListener {
                onItemClick.invoke(item)
            }
            binding.ivCategory.load(item.imageUrl){
                crossfade(true)
            }
            Log.d("Image url", item.imageUrl)
            binding.tvCategory.text = item.name
        }
    }
}