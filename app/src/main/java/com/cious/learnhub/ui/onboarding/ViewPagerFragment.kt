package com.cious.learnhub.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.cious.learnhub.R
import com.cious.learnhub.ui.onboarding.screen.OnBoardingScreenFirst
import com.cious.learnhub.ui.onboarding.screen.OnBoardingScreenSecond
import com.cious.learnhub.ui.onboarding.screen.OnBoardingScreenThird


class ViewPagerFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        val fragmentList = arrayListOf<Fragment>(
            OnBoardingScreenFirst(),
            OnBoardingScreenSecond(),
            OnBoardingScreenThird()
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        view.findViewById<ViewPager2>(R.id.viewPager).adapter = adapter

        return view
    }


}