package com.cious.learnhub.ui.myclass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cious.learnhub.databinding.FragmentHomeBinding
import com.cious.learnhub.databinding.FragmentMyClassBinding

class MyClassFragment : Fragment() {

    private lateinit var binding: FragmentMyClassBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyClassBinding.inflate(inflater, container, false)
        return binding.root

    }

}