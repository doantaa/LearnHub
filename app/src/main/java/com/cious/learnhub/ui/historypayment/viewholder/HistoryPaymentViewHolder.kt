package com.cious.learnhub.ui.historypayment.viewholder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
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
        binding.ivHistoryPayment.load(item.courseItemResponse?.imageUrl)
        binding.tvNameCourse.text = item.courseItemResponse?.category?.name
        binding.tvTitleCourse.text = item.courseItemResponse?.title
        binding.tvAuthorCourse.text = item.courseItemResponse?.instructor
        if(item.status == "settlement"){
            binding.tvPaymentSuccess.isVisible = true
            binding.tvPaymentPending.isVisible = false
            binding.tvPaymentExpired.isVisible = false
        } else if(item.status == "pending") {
            binding.tvPaymentPending.isVisible = true
            binding.tvPaymentSuccess.isVisible = false
            binding.tvPaymentExpired.isVisible = false
        } else {
            binding.tvPaymentExpired.isVisible = true
            binding.tvPaymentSuccess.isVisible = false
            binding.tvPaymentPending.isVisible = false
        }




    }
}



