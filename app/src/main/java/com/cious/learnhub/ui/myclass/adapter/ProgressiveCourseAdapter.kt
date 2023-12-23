package com.cious.learnhub.ui.myclass.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cious.learnhub.databinding.ItemListProgressiveCourseBinding
import com.cious.learnhub.model.Enrollment

class ProgressiveCourseAdapter(private val itemClick: (Enrollment) -> Unit) :
    RecyclerView.Adapter<ProgressiveCourseItemViewHolder>() {
    private val dataDiffer = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<Enrollment>() {
            override fun areItemsTheSame(
                oldItem: Enrollment,
                newItem: Enrollment
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Enrollment,
                newItem: Enrollment
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProgressiveCourseItemViewHolder {
        return ProgressiveCourseItemViewHolder(
            binding = ItemListProgressiveCourseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClick = itemClick
        )
    }

    override fun onBindViewHolder(holder: ProgressiveCourseItemViewHolder, position: Int) {
        holder.bindView(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    fun setData(data: List<Enrollment>) {
        dataDiffer.submitList(data)
    }

}

class ProgressiveCourseItemViewHolder(
    private val binding: ItemListProgressiveCourseBinding,
    private val itemClick: (Enrollment) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bindView(item: Enrollment) {
        with(item) {
            binding.ivClassImage.load(item.imageUrl) {
                crossfade(true)
            }
            binding.tvTitleClass.text = item.title
            binding.tvLevel.text = item.level
            binding.tvInstructor.text = item.instructor
            binding.tvDuration.text = item.totalDuration
            binding.tvModule.text = item.moduleCount.toString()
            binding.tvCategoryClass.text = item.title
            binding.tvRate.text = item.rating.toString()
            itemView.setOnClickListener { itemClick(item) }
        }
    }
}