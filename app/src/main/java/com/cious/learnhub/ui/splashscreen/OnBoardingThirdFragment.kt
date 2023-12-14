package com.cious.learnhub.ui.splashscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.cious.learnhub.R
import com.cious.learnhub.databinding.FragmentOnBoarding3Binding

class OnBoardingThirdFragment : Fragment() {

    private lateinit var binding: FragmentOnBoarding3Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoarding3Binding.inflate(inflater, container, false)
        val view = binding.root

        binding.btFinish.setOnClickListener {
            if (activity is OnboardingNavigation) {
                (activity as OnboardingNavigation).navigateToNextFragment(3)
            }
        }

        return view
    }
}