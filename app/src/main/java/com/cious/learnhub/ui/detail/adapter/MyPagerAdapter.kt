package com.cious.learnhub.ui.detail.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cious.learnhub.ui.detail.pagerfragment.AboutFragment
import com.cious.learnhub.ui.detail.pagerfragment.ClassMaterialFragment

class MyPagerAdapter(fragmentManager: FragmentManager,
                     lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0 ){
            AboutFragment()
        }else{
            ClassMaterialFragment()
        }
    }

}
