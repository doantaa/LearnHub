package com.cious.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cious.learnhub.databinding.ItemListHistoryPaymentBinding.inflate
import com.cious.learnhub.databinding.ItemListHistoryPaymentBinding
import com.cious.model.HistoryPayment

class HistoryPaymentAdapter(private val historyPayment: ArrayList<HistoryPayment>): RecyclerView.Adapter<HistoryPaymentAdapter.ListViewHolder>(){

    inner class ListViewHolder(private val binding : ItemListHistoryPaymentBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(historyPayment: HistoryPayment){
            Glide.with(itemView).load(historyPayment.imageCourse).into(binding.ivHistoryPayment)
            Glide.with(itemView).load(historyPayment.statusPayment).into(binding.ivStatusPayment)
            binding.tvNameCourse.text = historyPayment.nameCourse
            binding.tvRating.text = historyPayment.rating
            binding.tvTitleCourse.text = historyPayment.titleCourse
            binding.tvAuthorCourse.text = historyPayment.authorCourse
            binding.tvLevel.text = historyPayment.levelCourse
            binding.tvModul.text = historyPayment.modulCourse
            binding.tvDuration.text = historyPayment.duration
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HistoryPaymentAdapter.ListViewHolder = ListViewHolder(inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int = historyPayment.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) = holder.bind(historyPayment[position])

}