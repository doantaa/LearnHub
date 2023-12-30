package com.cious.learnhub.ui.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cious.learnhub.core.ViewHolderBinder
import com.cious.learnhub.databinding.NotificationItemsBinding
import com.cious.learnhub.model.NotificationModel

class NotificationsAdapter (
    val onItemClick: (NotificationModel) -> Unit
) : RecyclerView.Adapter<NotificationViewHolder>(){


    private val differ = AsyncListDiffer(this,object: DiffUtil.ItemCallback<NotificationModel>(){
        override fun areItemsTheSame(
            oldItem: NotificationModel,
            newItem: NotificationModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: NotificationModel,
            newItem: NotificationModel
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return NotificationViewHolder(
            binding = NotificationItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false),
            onItemClick
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        (holder as ViewHolderBinder<NotificationModel>).bind(differ.currentList[position])
    }

    fun setData(data:List<NotificationModel>){
        differ.submitList(data)
    }
}