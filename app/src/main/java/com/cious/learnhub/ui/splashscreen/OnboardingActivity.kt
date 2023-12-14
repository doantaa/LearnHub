package com.cious.learnhub.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cious.learnhub.R
import com.cious.learnhub.databinding.ActivityOnboardingBinding
import com.cious.learnhub.ui.main.MainActivity

class OnboardingActivity : AppCompatActivity(), OnboardingNavigation {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
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
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slider_in, R.anim.slider_out)
                finish()
            }
        }
    }
}