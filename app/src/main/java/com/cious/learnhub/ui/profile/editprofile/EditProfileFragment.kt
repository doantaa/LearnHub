package com.cious.learnhub.ui.profile.editprofile

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.cious.learnhub.R
import com.cious.learnhub.databinding.FragmentEditProfileBinding
import com.cious.learnhub.utils.proceedWhen

class EditProfileFragment : Fragment() {

    lateinit var binding : FragmentEditProfileBinding

    private lateinit var viewModel: EditProfileViewModel
    private val IMAGE_REQUEST_CODE = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_editProfileFragment_to_navigation_profile)
        }
        binding.constraintCircle.setOnClickListener {
            openFileChooser()
        }

    }

    private fun openFileChooser() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            binding.imgAbout.setImageURI(data?.data)
        }
    }
    private fun observeViewModel() {
        viewModel.profile.observe(viewLifecycleOwner) {
            it.proceedWhen (
                doOnSuccess = {
                   it.payload?.let {
                       binding.inputNameLay.setText(it.name)
                       binding.inputEmailLay.setText(it.email)
                       binding.inputNoPhoneLay.setText(it.phoneNumber)
                       binding.inputCountryLay.setText(it.country)
                       binding.inputCityLay.setText(it.city)
                   }

                }
            )
        }

    }


}