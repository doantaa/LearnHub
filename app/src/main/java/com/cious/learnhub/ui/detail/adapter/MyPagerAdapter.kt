package com.cious.learnhub.ui.detail.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cious.learnhub.ui.detail.pagerfragment.AboutFragment
import com.cious.learnhub.ui.detail.pagerfragment.ClassMaterialFragment

class MyPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = AboutFragment()
            1 -> fragment = ClassMaterialFragment()
        }
        return fragment as Fragment
    }

}
