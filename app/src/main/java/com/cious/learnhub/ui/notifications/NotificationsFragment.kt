package com.cious.learnhub.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cious.learnhub.R
import com.cious.learnhub.data.network.api.datasource.NotificationDataSourceImpl
import com.cious.learnhub.data.network.api.service.NotificationService
import com.cious.learnhub.data.repository.NotificationRepositoryImpl
import com.cious.learnhub.databinding.FragmentNotificationsBinding
import com.cious.learnhub.model.NotificationModel
import com.cious.learnhub.ui.authentication.login.LoginActivity
import com.cious.learnhub.ui.authentication.register.RegisterActivity
import com.cious.learnhub.ui.authentication.resetpassword.ResetPasswordActivity
import com.cious.learnhub.utils.GenericViewModelFactory
import com.cious.learnhub.utils.SessionManager
import com.cious.learnhub.utils.highLightWord
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
        checkTokenUser()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.incUserNotLogin.btnLogin.setOnClickListener {
            navigateToLogin()
        }
        binding.incUserNotLogin.tvIntentRegister.highLightWord(getString(R.string.text_highlight_register)) {
            navigateToRegister()
        }
    }

    private fun navigateToRegister() {
        startActivity(Intent(requireContext(), RegisterActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun navigateToLogin() {
        startActivity(Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun checkTokenUser() {
        val token = SessionManager.getToken(requireContext())
        if (token.isNullOrBlank()) {
            binding.clUserNotLogin.isVisible = true
            binding.clUserLogin.isVisible = false
        } else {
            binding.clUserNotLogin.isVisible = false
            binding.clUserLogin.isVisible = true
        }
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