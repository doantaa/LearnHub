package com.cious.learnhub.ui.splashscreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cious.learnhub.databinding.FragmentOnBoarding2Binding

class OnBoardingSecondFragment : Fragment() {

    private var _binding: FragmentOnBoarding2Binding? = null
    private val binding get() = _binding
    private var handler: Handler? = null
    private var runnable: Runnable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoarding2Binding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            navigateToNextPage()
        }
        runnable?.let {
            handler?.postDelayed(it, 4000)
        }

        binding?.btNext?.setOnClickListener {
            if (activity is OnboardingNavigation) {
                navigateToNextPage()
            }
        }
    }

    private fun navigateToNextPage() {
        (activity as OnboardingNavigation).navigateToNextFragment(2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        super.onDestroy()
        runnable?.let {
            handler?.removeCallbacks(it)
        }
        handler = null
        runnable = null
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}