package com.cious.learnhub.ui.splashscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.cious.learnhub.R
import com.cious.learnhub.databinding.FragmentOnBoarding2Binding

class OnBoardingSecondFragment : Fragment() {

    private lateinit var binding: FragmentOnBoarding2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoarding2Binding.inflate(inflater, container, false)
        val view = binding.root

        binding.btNext.setOnClickListener {
            if (activity is OnboardingNavigation) {
                (activity as OnboardingNavigation).navigateToNextFragment(2)
            }
        }

        return view
    }
}