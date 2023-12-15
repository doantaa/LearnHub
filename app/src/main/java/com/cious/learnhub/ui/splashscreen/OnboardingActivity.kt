package com.cious.learnhub.ui.splashscreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.cious.learnhub.R
import com.cious.learnhub.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity(), OnboardingNavigation {

    private val binding: ActivityOnboardingBinding by lazy {
        ActivityOnboardingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val firstFragment = OnBoardingFirstFragment()
        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(R.anim.slider_in, R.anim.slider_out)
            replace(binding.fragmentContainer.id, firstFragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun navigateToNextFragment(position: Int) {
        when (position) {
            1 -> {
                val secondFragment = OnBoardingSecondFragment()
                supportFragmentManager.beginTransaction().apply {
                    setCustomAnimations(R.anim.slider_in, R.anim.slider_out)
                    replace(binding.fragmentContainer.id, secondFragment)
                    addToBackStack(null)
                    commit()
                }
            }

            2 -> {
                val thirdFragment = OnBoardingThirdFragment()
                supportFragmentManager.beginTransaction().apply {
                    setCustomAnimations(R.anim.slider_in, R.anim.slider_out)
                    replace(binding.fragmentContainer.id, thirdFragment)
                    addToBackStack(null)
                    commit()
                }
            }

            3 -> {
                val firstFragment = OnBoardingFirstFragment()

                supportFragmentManager.popBackStack(
                    null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )

                supportFragmentManager.beginTransaction().apply {
                    setCustomAnimations(R.anim.slider_in, R.anim.slider_out)
                    replace(binding.fragmentContainer.id, firstFragment)
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }
}