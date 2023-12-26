package com.cious.learnhub.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cious.learnhub.data.network.api.datasource.NotificationDataSourceImpl
import com.cious.learnhub.data.network.api.service.NotificationService
import com.cious.learnhub.data.repository.NotificationRepositoryImpl
import com.cious.learnhub.databinding.FragmentNotificationsBinding
import com.cious.learnhub.model.NotificationModel
import com.cious.learnhub.utils.GenericViewModelFactory
import com.cious.learnhub.utils.SessionManager
import com.cious.learnhub.utils.proceedWhen

class NotificationsFragment : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding
    private val notificationsAdapter: NotificationsAdapter by lazy {
        NotificationsAdapter {
            markAsReadNotification(it)
        }
    }
    private val viewModel: NotificationsViewModel by viewModels {
        val service =
            NotificationService.invoke(ChuckerInterceptor(requireContext()), requireContext())
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
        observeViewModel()

        val token = SessionManager.getToken(requireContext())
        Log.d("ini token", token.orEmpty())
    }

    private fun observeViewModel() {
        viewModel.notification.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let {
                        notificationsAdapter.setData(it)
                    }
                }
            )
        }
    }

    private fun markAsReadNotification(data: NotificationModel) {
        if (!data.isRead) {
            binding.pbLoading.isVisible = true
            Log.d("MarkRead", data.id.toString())
            viewModel.markedNotification(data.id).observe(viewLifecycleOwner) {
                it.proceedWhen(
                    doOnSuccess = {
                        Log.d("MarkRead", "Success")
                        binding.pbLoading.isVisible = false
                        observeViewModel()
                    },
                    doOnLoading = {
                        binding.pbLoading.isVisible = true
                    },
                    doOnError = {
                        Log.d("MarkRead", "Failed")
                        binding.pbLoading.isVisible = false
                        Toast.makeText(context, it.exception?.message, Toast.LENGTH_LONG).show()
                    }
                )
            }
        }
    }

    private fun observeNotificationData() {
        notificationsAdapter.setData(notificationDummy)
    }

    private fun setupRecyclerView() {
        binding.rvNotification.apply {
            adapter = notificationsAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    LinearLayoutManager.VERTICAL
                )
            )
        }
    }

    val notificationDummy = listOf(
        NotificationModel(
            1, "Promosi",
            "2 Maret, 12:00",
            "Dapatkan Potongan 50% selama Bulan Maret!",
            "Syarat dan Ketentuan berlaku!",
            true
        ),
        NotificationModel(
            2,
            "Promosi",
            "2 Maret, 12:00",
            "Dapatkan Potongan 50% selama Bulan Maret!",
            "Syarat dan Ketentuan berlaku!",
            false
        ),
        NotificationModel(
            3,
            "Promosi",
            "2 Maret, 12:00",
            "Dapatkan Potongan 50% selama Bulan Maret!",
            "Syarat dan Ketentuan berlaku!",
            false
        ),
        NotificationModel(
            4,
            "Promosi",
            "2 Maret, 12:00",
            "Dapatkan Potongan 50% selama Bulan Maret!",
            "Syarat dan Ketentuan berlaku!",
            true
        ),
        NotificationModel(
            5,
            "Promosi",
            "2 Maret, 12:00",
            "Dapatkan Potongan 50% selama Bulan Maret!",
            "Syarat dan Ketentuan berlaku!",
            false
        ),
    )
}