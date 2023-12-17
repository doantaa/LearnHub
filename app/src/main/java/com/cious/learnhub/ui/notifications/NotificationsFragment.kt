package com.cious.learnhub.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cious.learnhub.data.network.api.datasource.NotificaitonDataSource
import com.cious.learnhub.data.network.api.datasource.NotificationDataSourceImpl
import com.cious.learnhub.data.network.api.service.AuthenticationService
import com.cious.learnhub.data.network.api.service.NotificationService
import com.cious.learnhub.data.repository.NotificationRepositoryImpl
import com.cious.learnhub.databinding.FragmentNotificationsBinding
import com.cious.learnhub.model.NotificationModel
import com.cious.learnhub.utils.GenericViewModelFactory
import com.cious.learnhub.utils.SessionManager
import com.cious.learnhub.utils.proceedWhen

class NotificationsFragment : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding
    private val notificationsAdapter:NotificationsAdapter by lazy{
        NotificationsAdapter{

        }
    }
    private val viewModel: NotificationsViewModel by viewModels {
        val service = NotificationService.invoke(ChuckerInterceptor(requireContext()), requireContext())
        val dataSource = NotificationDataSourceImpl(service)
        val repository = NotificationRepositoryImpl(dataSource)
        GenericViewModelFactory.create(NotificationsViewModel(repository))
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
        observeViewModel()

        val token = SessionManager.getToken(requireContext())
        Log.d("ini token", token.orEmpty())
    }

    private fun observeViewModel() {
        viewModel.notification.observe(viewLifecycleOwner) {
            it.proceedWhen (
                doOnSuccess = {
                    it.payload?.let {
                        notificationsAdapter.setData(it)
                    }
                }
            )
        }
    }

    private fun observeNotificationData() {
//        notificationsAdapter.setData(notificationDummy)
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