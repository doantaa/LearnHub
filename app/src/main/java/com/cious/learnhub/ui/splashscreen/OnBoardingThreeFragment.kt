package com.cious.learnhub.ui.splashscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.cious.learnhub.R

class OnBoardingThirdFragment : Fragment() {
    private lateinit var btNext: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_on_boarding3, container, false)
        btNext = view.findViewById(R.id.btFinish)

        btNext.setOnClickListener {
            if (activity is OnboardingNavigation) {
                (activity as OnboardingNavigation).navigateToNextFragment(3)
            }
        }

        return view
    }

}