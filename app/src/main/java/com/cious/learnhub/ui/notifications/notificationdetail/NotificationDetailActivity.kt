package com.cious.learnhub.ui.notifications.notificationdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cious.learnhub.databinding.ActivityNotificationDetailBinding
import com.cious.learnhub.model.NotificationModel
import com.cious.learnhub.utils.toDate
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class NotificationDetailActivity : AppCompatActivity() {
    private val binding: ActivityNotificationDetailBinding by lazy {
        ActivityNotificationDetailBinding.inflate(
            layoutInflater
        )
    }
    private val viewModel: NotificationDetailViewModel by viewModel { parametersOf(intent?.extras)  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindData(viewModel.notification)
    }


    private fun bindData(notification: NotificationModel?) {
        notification?.let { item->
            binding.tvTitle.text=item.title
            binding.tvDescription.text=item.description
            binding.tvDatetime.text=item.datetime.toDate()
            binding.tvCategory.text=item.category
        }
    }

    companion object {
        const val EXTRA_NOTIFICATION = "EXTRA_NOTIFICATION"
        fun startActivity(context: Context, notification : NotificationModel) {
            val intent = Intent(context, NotificationDetailActivity::class.java)
            intent.putExtra(EXTRA_NOTIFICATION, notification)
            context.startActivity(intent)
        }
    }
}