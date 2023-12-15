package com.cious.learnhub.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cious.learnhub.databinding.FragmentOnBoarding3Binding
import com.cious.learnhub.ui.authentication.login.LoginActivity
import com.cious.learnhub.ui.authentication.register.RegisterActivity
import com.cious.learnhub.ui.main.MainActivity

class OnBoardingThirdFragment : Fragment() {

    private var _binding: FragmentOnBoarding3Binding? = null
    private val binding get() = _binding
    private var handler: Handler? = null
    private var runnable: Runnable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoarding3Binding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            navigateToFirstPage()
        }
        runnable?.let {
            handler?.postDelayed(it, 4000)
        }

        initListener()
    }

    private fun initListener() {
        binding?.run {
            btnRegister.setOnClickListener {
                navigateToRegister()
            }

            btnTextLogin.setOnClickListener {
                navigateToLogin()
            }

            btnEnterWithoutLogin.setOnClickListener {
                navigateToHome()
            }
        }
    }

    private fun navigateToFirstPage() {
        (activity as OnboardingNavigation).navigateToNextFragment(3)
    }

    private fun navigateToRegister() {
        val intent = Intent(context, RegisterActivity::class.java)
        context?.startActivity(intent)
        requireActivity().finish()
    }

    private fun navigateToLogin() {
        val intent = Intent(context, LoginActivity::class.java)
        context?.startActivity(intent)
        requireActivity().finish()
    }

    private fun navigateToHome() {
        val intent = Intent(context, MainActivity::class.java)
        context?.startActivity(intent)
        requireActivity().finish()
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