package com.cious.learnhub.ui.course

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cious.learnhub.core.ViewHolderBinder
import com.cious.learnhub.databinding.ItemCategoryBinding

class CourseTypeListAdapter(
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<CourseTypeViewHolder>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseTypeViewHolder {
        return CourseTypeViewHolder(
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

    override fun onBindViewHolder(holder: CourseTypeViewHolder, position: Int) {
        (holder as ViewHolderBinder<String>).bind(differ.currentList[position])
    }

    fun setData(data: List<String>) {
        differ.submitList(data)
    }
}