package com.cious.learnhub.ui.profile.editprofile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
import com.cious.learnhub.R
import com.cious.learnhub.data.network.api.model.profile.ProfileRequest
import com.cious.learnhub.databinding.FragmentEditProfileBinding
import com.cious.learnhub.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileFragment : Fragment() {

    lateinit var binding: FragmentEditProfileBinding

    private val viewModel: EditProfileViewModel by viewModel()
    private val IMAGE_REQUEST_CODE = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        observeEditProfile()

        binding.btnBack.setOnClickListener {
            navigateToProfile()
        }
        binding.constraintCircle.setOnClickListener {
            openFileChooser()
        }
        binding.btnSave.setOnClickListener {
            doEditData()
        }
    }

    private fun navigateToProfile() {
        findNavController().navigate(R.id.action_editProfileFragment_to_navigation_profile)
    }

    private fun observeEditProfile() {
        viewModel.profilefRequestResult.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnLoading = {
                    binding.btnSave.isVisible = false
                    binding.pbLoading.isVisible = true
                },
                doOnSuccess = {
                    binding.btnSave.isVisible = true
                    binding.pbLoading.isVisible = false
                    Toast.makeText(requireContext(),
                        getString(R.string.edit_profile_success), Toast.LENGTH_SHORT)
                        .show()
                    navigateToProfile()
                },
                doOnError = {
                    binding.btnSave.isVisible = true
                    binding.pbLoading.isVisible = false
                    Toast.makeText(requireContext(),
                        getString(R.string.edit_profile_failed), Toast.LENGTH_SHORT)
                        .show()
                }

            )
        }
    }

    private fun doEditData() {
        val name = binding.inputNameLay.text.toString()
        val image = ""
        val email = binding.inputEmailLay.text.toString()
        val phoneNumber = binding.inputNoPhoneLay.text.toString().toLong()
        val country = binding.inputCountryLay.text.toString()
        val city = binding.inputCityLay.text.toString()
        val profileRequest = ProfileRequest(image, name, email, phoneNumber, country, city)
        viewModel.doEditData(profileRequest)
    }

    private fun openFileChooser() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            binding.imgAbout.setImageURI(data?.data)
        }
    }

    private fun observeViewModel() {
        viewModel.profile.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let {
                        binding.inputNameLay.setText(it.name)
                        binding.inputEmailLay.setText(it.email)
                        binding.inputNoPhoneLay.setText(it.phoneNumber)
                        binding.inputCountryLay.setText(it.country)
                        binding.inputCityLay.setText(it.city)
                        binding.imgAbout.load(it.profileUrl)
                        binding.pbLoading.isVisible = false
                        binding.svProfile.isVisible  = true
                    }
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.svProfile.isVisible = false
                }
            )
        }

    }


}