package com.cious.learnhub.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cious.learnhub.R
import com.cious.learnhub.databinding.FragmentProfileBinding
import com.cious.learnhub.ui.historypayment.HistoryPaymentActivity
import com.cious.learnhub.utils.SessionManager
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
        binding.tvLogout.setOnClickListener {
            viewModel.clearToken()
            Toast.makeText(requireContext(), "logout successfully", Toast.LENGTH_SHORT).show()
        }
    }

}