package com.cious.learnhub.ui.splashscreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.cious.learnhub.databinding.FragmentOnBoarding1Binding

class OnBoardingFirstFragment : Fragment() {

    private var _binding: FragmentOnBoarding1Binding? = null
    private val binding get() = _binding
    private var handler: Handler? = null
    private var runnable: Runnable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoarding1Binding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })

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
        (activity as OnboardingNavigation).navigateToNextFragment(1)
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