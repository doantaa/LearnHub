package com.cious.learnhub.ui.myclass.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.cious.learnhub.databinding.FragmentCourseDetailBinding

class CourseDetailFragment : Fragment() {

    private lateinit var binding: FragmentCourseDetailBinding
    lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCourseDetailBinding.inflate(inflater, container, false)
        /*
        viewPager = binding.pager
        val adapter = MyPagerAdapter(this)
        viewPager.adapter = adapter
         */
        return binding.root
    }
}