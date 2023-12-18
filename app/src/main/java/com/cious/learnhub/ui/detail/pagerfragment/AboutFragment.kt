package com.cious.learnhub.ui.detail.pagerfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.cious.learnhub.R
import com.cious.learnhub.databinding.FragmentAboutBinding
import com.cious.learnhub.ui.detail.CourseDetailViewModel
import com.cious.learnhub.utils.proceedWhen

class AboutFragment : Fragment() {

    private lateinit var viewModel: CourseDetailViewModel

    private lateinit var binding: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        viewModel.courses.observe(viewLifecycleOwner){
            it.proceedWhen (
                doOnSuccess = {
                    it.payload.let {
                        binding.tvAbout.text = it?.about
                        binding.tvObjective.text = it?.objective
                    }
                }
            )
        }
    }

}