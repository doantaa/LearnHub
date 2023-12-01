package com.cious.learnhub.Nofitcations.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cious.learnhub.R

class NotificationAdapter(private val notificationList: List<NotificationModel>) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.text_title)
        val datetime: TextView = itemView.findViewById(R.id.text_datetime)
        val description: TextView = itemView.findViewById(R.id.text_description)
        val description2: TextView = itemView.findViewById(R.id.text_description_2)
        val imgIndikator: ImageView = itemView.findViewById(R.id.indicator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.notification_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = notificationList[position]
        holder.title.text = currentItem.title
        holder.datetime.text = currentItem.datetime
        holder.description.text = currentItem.description
        holder.description2.text = currentItem.description2
        holder.imgIndikator.setImageDrawable(currentItem.indicator)
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }
}