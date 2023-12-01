package com.cious.learnhub.ui.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cious.learnhub.core.ViewHolderBinder
import com.cious.learnhub.databinding.ItemCourseBinding
import com.cious.learnhub.model.Course
import com.cious.learnhub.ui.home.adapter.viewholder.CourseListViewHolder

class CourseListAdapter(
    private val onItemClick: (Course) -> Unit
): RecyclerView.Adapter<CourseListViewHolder>() {

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Course>(){
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseListViewHolder {
        return CourseListViewHolder(
            binding = ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClick
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CourseListViewHolder, position: Int) {
        (holder as ViewHolderBinder<Course>).bind(differ.currentList[position])
    }

    fun setData(courseDataList: List<Course>){
        differ.submitList(courseDataList)
        Log.d("DIFFER SIZE", courseDataList.toString())
    }

}