package com.cious.learnhub.Splashscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.cious.learnhub.R

class OnBoardingFirstFragment : Fragment() {
    private lateinit var btNext: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_on_boarding1, container, false)
        btNext = view.findViewById(R.id.btNext)

        btNext.setOnClickListener {
            if (activity is OnboardingNavigation) {
                (activity as OnboardingNavigation).navigateToNextFragment(1)
            }
        }

        return view
    }
}