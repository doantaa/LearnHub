package com.cious.learnhub.ui.historypayment.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.cious.learnhub.core.ViewHolderBinder
import com.cious.learnhub.databinding.ItemListHistoryPaymentPaidBinding
import com.cious.learnhub.databinding.ItemListHistoryPaymentUnpaidBinding
import com.cious.learnhub.model.HistoryPayment

class HistoryPaymentPaidViewHolder(
    private val binding: ItemListHistoryPaymentPaidBinding,
    private val onClickListener: (HistoryPayment) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<HistoryPayment> {
    override fun bind(item: HistoryPayment) {
        binding.tvNameCourse.text= "UIUX"
        binding.tvRating.text=item.rating.toString()
        binding.tvTitleCourse.text=item.name
        binding.tvAuthorCourse.text=item.author
        binding.tvLevel.text=item.level
        binding.tvModul.text=item.modul
        binding.tvDuration.text=item.duration
    }
}

class HistoryPaymentUnPaidViewHolder(
    private val binding: ItemListHistoryPaymentUnpaidBinding,
    private val onClickListener: (HistoryPayment) -> Unit
): RecyclerView.ViewHolder(binding.root), ViewHolderBinder<HistoryPayment>{
    override fun bind(item: HistoryPayment) {
        binding.tvNameCourse.text=item.name
        binding.tvRating.text=item.rating.toString()
        binding.tvTitleCourse.text=item.name
        binding.tvAuthorCourse.text=item.author
        binding.tvLevel.text=item.level
        binding.tvModul.text=item.modul
        binding.tvDuration.text=item.duration
    }

}