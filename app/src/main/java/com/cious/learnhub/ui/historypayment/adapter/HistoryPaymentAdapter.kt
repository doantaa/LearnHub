package com.cious.learnhub.ui.historypayment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cious.learnhub.core.ViewHolderBinder
import com.cious.learnhub.databinding.ItemListHistoryPaymentPaidBinding
import com.cious.learnhub.databinding.ItemListHistoryPaymentUnpaidBinding
import com.cious.learnhub.model.HistoryPayment
import com.cious.learnhub.model.UserTransaction
import com.cious.learnhub.ui.historypayment.viewholder.HistoryPaymentPaidViewHolder
import com.cious.learnhub.ui.historypayment.viewholder.HistoryPaymentUnPaidViewHolder

class HistoryPaymentAdapter (
        var courseTypeAdapter: HistoryPaymentTypeAdapter,
        private val onItemClick: (UserTransaction) -> Unit
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<UserTransaction>() {
            override fun areItemsTheSame(
                oldItem: UserTransaction,
                newItem: UserTransaction
            ): Boolean {
             return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UserTransaction,
                newItem: UserTransaction
            ): Boolean {
               return oldItem.hashCode() == newItem.hashCode()
            }

        })

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                HistoryPaymentTypeAdapter.PAID.ordinal -> (
                        HistoryPaymentPaidViewHolder(
                            binding = ItemListHistoryPaymentPaidBinding.inflate(
                                LayoutInflater.from(parent.context),
                                parent,
                                false
                            ),
                            onItemClick

                        )
                        )

                else -> {
                    HistoryPaymentUnPaidViewHolder(
                        binding = ItemListHistoryPaymentUnpaidBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        ),
                        onItemClick
                    )
                }
            }
        }

        fun setData(data: List<UserTransaction>) {
            differ.submitList(data)
        }

        override fun getItemCount(): Int = differ.currentList.size

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as ViewHolderBinder<UserTransaction>).bind(differ.currentList[position])
        }

        fun refreshList() {
            notifyItemRangeChanged(0, differ.currentList.size)
        }
}