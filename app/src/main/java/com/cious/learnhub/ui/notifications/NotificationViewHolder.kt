package com.cious.learnhub.ui.notifications

import androidx.recyclerview.widget.RecyclerView
import com.cious.learnhub.core.ViewHolderBinder
import com.cious.learnhub.databinding.NotificationItemsBinding
import com.cious.learnhub.model.NotificationModel
import com.cious.learnhub.utils.toDate

class NotificationViewHolder (
    private val binding: NotificationItemsBinding,
    val onItemClick: (NotificationModel) -> Unit

): RecyclerView.ViewHolder(binding.root), ViewHolderBinder<NotificationModel> {
    override fun bind(item: NotificationModel) {
        with(item){
            binding.textCategory.text=item.category.capitalize()
            binding.textDatetime.text=item.datetime.toDate()
            binding.textTitle.text=item.title
            binding.textDescription.text=item.description
        }
    }

}

