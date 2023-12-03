package com.cious.learnhub.ui.notification

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotificationFragment : Fragment() {

    private lateinit var rcNotif: RecyclerView
    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var notificationList: List<NotificationModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notification, container, false)
        rcNotif = view.findViewById(R.id.rcNotification)

        notificationList = createNotificationList()
        notificationAdapter = NotificationAdapter(notificationList)

        rcNotif.layoutManager = LinearLayoutManager(requireContext())
        rcNotif.adapter = notificationAdapter

        return view
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun createNotificationList(): List<NotificationModel> {
        return listOf(
            NotificationModel(
                "Promosi",
                "2 Maret, 12:00",
                "Dapatkan Potongan 50% selama Bulan Maret!",
                "Syarat dan Ketentuan berlaku!",
                requireContext().getDrawable(R.drawable.ic_indikator1)!!
            ),
            NotificationModel(
                "Notifikasi",
                "1 Maret, 10:00",
                "Password berhasil diubah",
                "",
                requireContext().getDrawable(R.drawable.ic_indikator2)!!
            ),

            )
    }
}