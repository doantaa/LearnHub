package com.cious.learnhub.ui.onboarding.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.cious.learnhub.R
import com.google.android.material.button.MaterialButton


class OnBoardingScreenFirst : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_on_boarding_screen_first, container, false)
        val viewPager =  activity?.findViewById<ViewPager2>(R.id.viewPager)

        view.findViewById<MaterialButton>(R.id.btn_next1).setOnClickListener {
            viewPager?.currentItem = 1
        }
        return view
    }


}