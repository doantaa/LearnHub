package com.cious.learnhub.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cious.learnhub.R
import com.cious.learnhub.databinding.FragmentProfileBinding
import com.cious.learnhub.databinding.SheetAuthenticationRequeredBinding
import com.cious.learnhub.ui.authentication.login.LoginActivity
import com.cious.learnhub.ui.historypayment.HistoryPaymentActivity
import com.cious.learnhub.ui.main.MainActivity
import com.cious.learnhub.utils.SessionManager
import com.google.android.material.bottomsheet.BottomSheetDialog

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var sheetBinding: SheetAuthenticationRequeredBinding
    private lateinit var dialog: BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkTokenUser()

        binding.constraintEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_editProfileFragment)
        }
        binding.constraintChangesPassword.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_changePasswordFragment)
        }
        binding.constraintHistoryPayment.setOnClickListener {
            val intent = Intent(requireContext(), HistoryPaymentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkTokenUser() {
        val token = SessionManager.getToken(requireContext())
        if (token.isNullOrBlank()) {
            bottomSheetAuthRequered()
        }
    }

    private fun bottomSheetAuthRequered() {
        sheetBinding = SheetAuthenticationRequeredBinding.inflate(layoutInflater)
        dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetTheme)
        dialog.setContentView(sheetBinding.root)
        dialog.show()

        sheetBinding.btnLogin.setOnClickListener {
            navigateToLogin()
        }
        sheetBinding.ibBack.setOnClickListener {
            navigateToHome()
        }
    }

    private fun navigateToHome() {
        startActivity(Intent(requireContext(), MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun navigateToLogin() {
        startActivity(Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

        })
    }

}