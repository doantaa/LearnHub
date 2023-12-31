package com.cious.learnhub.ui.profile.changepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cious.learnhub.R
import com.cious.learnhub.data.network.api.model.profile.ChangePasswordRequest
import com.cious.learnhub.databinding.FragmentChangePasswordBinding
import com.cious.learnhub.utils.ApiException
import com.cious.learnhub.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangePasswordFragment : Fragment() {
    lateinit var binding : FragmentChangePasswordBinding


    private  val viewModel: ChangePasswordViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangePasswordBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeResult()

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_changePasswordFragment_to_navigation_profile)
        }
        binding.btnSaveChangePassword.setOnClickListener {
            doChangePassword()
        }
    }

    private fun observeResult() {
        viewModel.changePasswordRequestResult.observe(viewLifecycleOwner){
            it.proceedWhen (
             doOnLoading = {
                 binding.pbLoading.isVisible=true
                 binding.btnSaveChangePassword.isVisible=false
             },
                doOnSuccess = {
                    binding.pbLoading.isVisible=false
                    binding.btnSaveChangePassword.isVisible=true
                    Toast.makeText(requireContext(), "Password successfully Changed", Toast.LENGTH_SHORT).show()
                },
                doOnError = {
                    binding.pbLoading.isVisible=false
                    binding.btnSaveChangePassword.isVisible=true
                    if (it.exception is ApiException) {
                        val message = it.exception.getParsedError()?.message.orEmpty()
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }

                }

            )


        }
    }

    private fun doChangePassword() {
        val oldPassword=binding.inputOldPassword.text.toString()
        val newPassword=binding.inputNewPassword.text.toString()
        val repeatPassword=binding.inputReNewPassword.text.toString()
        val changePasswordRequest=ChangePasswordRequest(oldPassword,newPassword,repeatPassword)
        viewModel.doChangePassword(changePasswordRequest)
    }
}