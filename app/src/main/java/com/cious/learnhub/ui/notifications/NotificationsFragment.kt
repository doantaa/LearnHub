package com.cious.learnhub.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.cious.learnhub.R
import com.cious.learnhub.databinding.FragmentNotificationsBinding
import com.cious.learnhub.ui.authentication.login.LoginActivity
import com.cious.learnhub.ui.authentication.register.RegisterActivity
import com.cious.learnhub.utils.ApiException
import com.cious.learnhub.utils.highLightWord
import com.cious.learnhub.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationsFragment : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding
    private val notificationsAdapter: NotificationsAdapter by lazy {
        NotificationsAdapter {

        }
    }
    private val viewModel: NotificationsViewModel by viewModel()

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
        startActivity(Intent(requireContext(), RegisterActivity::class.java))
    }

    private fun navigateToLogin() {
        startActivity(Intent(requireContext(), LoginActivity::class.java))
    }

    private fun checkTokenUser() {
        if (!viewModel.isLogin) {
            binding.clUserNotLogin.isVisible = true
            binding.clUserLogin.isVisible = false
        } else {
            binding.clUserNotLogin.isVisible = false
            binding.clUserLogin.isVisible = true
        }
    }

    private fun observeViewModel() {
        viewModel.notifRequestResult.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.rvNotification.isVisible = false
                },
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.rvNotification.isVisible = true
                    it.payload?.let {
                        notificationsAdapter.setData(it)
                    }
                },
                doOnError = {
                    if (it.exception is ApiException) {
                        val message = it.exception.getParsedError()?.message.orEmpty()
                        if (message == getString(R.string.text_jwt_expired)) {
//                            SessionManager.clearData(requireContext())
                        }
                    }
                    checkTokenUser()
                }
            )
        }
    }

    private fun observeNotificationData() {
        viewModel.getNotification()
    }

    private fun setupRecyclerView() {
        binding.rvNotification.adapter = notificationsAdapter
    }
}