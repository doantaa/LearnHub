package com.cious.learnhub.ui.notifications

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cious.learnhub.R
import com.cious.learnhub.core.ViewHolderBinder
import com.cious.learnhub.databinding.NotificationItemsBinding
import com.cious.learnhub.model.NotificationModel

class NotificationViewHolder(
    private val binding: NotificationItemsBinding,
    val onItemClick: (NotificationModel) -> Unit

) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<NotificationModel> {
    override fun bind(item: NotificationModel) {
        with(binding) {
            root.setOnClickListener {
                onItemClick.invoke(item)
            }
            textCategory.text = item.category
            textDatetime.text = item.datetime
            textTitle.text = item.title
            textDescription.text = item.description

            imgRead.background = if (item.isRead) {
                ContextCompat.getDrawable(root.context, R.drawable.ic_indikator1)
            } else ContextCompat.getDrawable(root.context, R.drawable.ic_indikator2)
        }
    }

}

