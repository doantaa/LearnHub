package com.cious.learnhub.ui.historypayment.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.cious.learnhub.core.ViewHolderBinder
import com.cious.learnhub.databinding.ItemListHistoryPaymentPaidBinding
import com.cious.learnhub.databinding.ItemListHistoryPaymentUnpaidBinding
import com.cious.learnhub.model.UserTransaction

class HistoryPaymentPaidViewHolder(
    private val binding: ItemListHistoryPaymentPaidBinding,
    private val onClickListener: (UserTransaction) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<UserTransaction> {
    override fun bind(item: UserTransaction) {
        binding.root.setOnClickListener {
            onClickListener.invoke(item)
        }
        binding.tvNameCourse.text = item.courseItemResponse?.category?.name
        binding.tvRating.text = item.courseItemResponse?.rating.toString()
        binding.tvTitleCourse.text = item.courseItemResponse?.title
        binding.tvAuthorCourse.text = item.courseItemResponse?.instructor
        binding.tvLevel.text = item.courseItemResponse?.level
        binding.tvModul.text = item.courseItemResponse?.moduleCount.toString()
        binding.tvDuration.text = item.courseItemResponse?.duration.toString()

    }
}

class HistoryPaymentUnPaidViewHolder(
    private val binding: ItemListHistoryPaymentUnpaidBinding,
    private val onClickListener: (UserTransaction) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<UserTransaction> {
    override fun bind(item: UserTransaction) {
        binding.tvNameCourse.text = item.courseItemResponse?.category?.name
        binding.tvRating.text = item.courseItemResponse?.rating.toString()
        binding.tvTitleCourse.text = item.courseItemResponse?.title
        binding.tvAuthorCourse.text = item.courseItemResponse?.instructor
        binding.tvLevel.text = item.courseItemResponse?.level
        binding.tvModul.text = item.courseItemResponse?.moduleCount.toString()
        binding.tvDuration.text = item.courseItemResponse?.duration.toString()
    }

}