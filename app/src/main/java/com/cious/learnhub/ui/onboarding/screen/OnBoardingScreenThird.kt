package com.cious.learnhub.ui.onboarding.screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cious.learnhub.R
import com.cious.learnhub.ui.authentication.login.LoginActivity
import com.cious.learnhub.ui.authentication.register.RegisterActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView


class OnBoardingScreenThird : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_on_boarding_screen_third, container, false)

        view.findViewById<MaterialButton>(R.id.btn_daftar).setOnClickListener {
            startActivity(Intent(requireActivity(),RegisterActivity::class.java))
            onBoardingFinished()
        }
        view.findViewById<MaterialTextView>(R.id.tv_masuk_disini).setOnClickListener {
            startActivity(Intent(requireActivity(),LoginActivity::class.java))
            onBoardingFinished()
        }
        view.findViewById<MaterialTextView>(R.id.tv_masuk_tanpa_login).setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_navigation_home)
            onBoardingFinished()
        }

        return view
    }

    private fun onBoardingFinished() {
        val sharePref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharePref.edit()
        editor.putBoolean("Finished",true)
        editor.apply()
    }
}