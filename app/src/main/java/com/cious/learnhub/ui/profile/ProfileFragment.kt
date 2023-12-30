package com.cious.learnhub.ui.profile

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cious.learnhub.R
import com.cious.learnhub.databinding.FragmentProfileBinding
import com.cious.learnhub.ui.authentication.login.LoginActivity
import com.cious.learnhub.ui.authentication.register.RegisterActivity
import com.cious.learnhub.ui.historypayment.HistoryPaymentActivity
import com.cious.learnhub.ui.main.MainActivity
import com.cious.learnhub.utils.highLightWord
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModel()

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
        setClickListeners()
    }

    private fun setClickListeners() {
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
        binding.constraintLogout.setOnClickListener {
            showConfirmLogoutPressDialog()
        }
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

    private fun showConfirmLogoutPressDialog() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle(getString(R.string.text_keluar))
        builder.setMessage(getString(R.string.text_apakah_anda_yakin_ingin_keluar))

        builder.setPositiveButton(getString(R.string.text_ya)) { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
            viewModel.clearToken()
            navigateToHome()
            Toast.makeText(requireContext(),
                getString(R.string.text_logout_successfully), Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton(getString(R.string.text_tidak)) { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }
        builder.show()
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

    private fun navigateToHome() {
        startActivity(Intent(requireContext(), MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}