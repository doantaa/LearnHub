package com.cious.learnhub.ui.historypayment.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.cious.learnhub.R
import com.cious.learnhub.core.ViewHolderBinder
import com.cious.learnhub.databinding.ItemListHistoryPaymentBinding
import com.cious.learnhub.model.UserTransaction

class HistoryPaymentPaidViewHolder(
    private val binding: ItemListHistoryPaymentBinding,
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
        if (item.status=="pending"){
            binding.ivStatusPayment.setImageResource(R.drawable.iv_waiting_payment)

        }else{
            binding.ivStatusPayment.setImageResource(R.drawable.iv_payment_success)
        }


    }
}



