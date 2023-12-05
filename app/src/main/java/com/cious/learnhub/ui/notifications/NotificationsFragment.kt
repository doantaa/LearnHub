package com.cious.learnhub.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cious.learnhub.databinding.FragmentNotificationsBinding
import com.cious.learnhub.model.NotificationModel

class NotificationsFragment : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding
    private val notificationsAdapter:NotificationsAdapter by lazy{
        NotificationsAdapter{

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeNotificationData()
    }

    private fun observeNotificationData() {
        notificationsAdapter.setData(notificationDummy)
    }

    private fun setupRecyclerView() {
        binding.rvNotification.adapter=notificationsAdapter
    }

    val notificationDummy= listOf<NotificationModel>(
        NotificationModel(
            1, "Promosi",
            "2 Maret, 12:00",
            "Dapatkan Potongan 50% selama Bulan Maret!",
            "Syarat dan Ketentuan berlaku!"
        ),
        NotificationModel(
            2,
            "Promosi",
            "2 Maret, 12:00",
            "Dapatkan Potongan 50% selama Bulan Maret!",
            "Syarat dan Ketentuan berlaku!"
        ),
        NotificationModel(
            3,
            "Promosi",
            "2 Maret, 12:00",
            "Dapatkan Potongan 50% selama Bulan Maret!",
            "Syarat dan Ketentuan berlaku!"
        ),
        NotificationModel(
            4,
            "Promosi",
            "2 Maret, 12:00",
            "Dapatkan Potongan 50% selama Bulan Maret!",
            "Syarat dan Ketentuan berlaku!"
        ),
        NotificationModel(
            5,
            "Promosi",
            "2 Maret, 12:00",
            "Dapatkan Potongan 50% selama Bulan Maret!",
            "Syarat dan Ketentuan berlaku!"
        ),
    )
}