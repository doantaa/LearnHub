package com.cious.learnhub.ui.myclass.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.cious.learnhub.databinding.FragmentCourseDetailBinding
import com.cious.learnhub.ui.home.HomeViewModel
import com.cious.learnhub.ui.home.adapter.CategoryListAdapter
import com.cious.learnhub.ui.myclass.detail.adapter.MyPagerAdapter
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class CourseDetailFragment : Fragment() {

    private lateinit var binding: FragmentCourseDetailBinding
    private val tabLayout: TabLayout by lazy {
        binding.tlDetail
    }
    private val viewPager2: ViewPager2 by lazy {
        binding.viewPager2
    }

    private val adapter: MyPagerAdapter by lazy {
        MyPagerAdapter(childFragmentManager, lifecycle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCourseDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLayout()
    }

    private fun showLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("Tentang"))
        tabLayout.addTab(tabLayout.newTab().setText("Materi Kelas"))

        viewPager2.adapter = adapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager2.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }
}