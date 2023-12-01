package com.cious.learnhub.ui.myclass.detail.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

/*
class MyPagerAdapter(fragment: Fragment) : FragmentStatePagerAdapter(fragment) {
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> CourseDescriptionFragment()
            1 -> CourseReviewFragment()
            else -> CourseProgressFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Deskripsi"
            1 -> "Ulasan"
            else -> "Kemajuan"
        }
    }

}

 */