package com.cious.learnhub.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cious.learnhub.R
import com.cious.learnhub.ui.main.MainActivity

class OnboardingActivity : AppCompatActivity(), OnboardingNavigation {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val firstFragment = OnBoardingFirstFragment()
        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(R.anim.slider_in, R.anim.slider_out)
            replace(R.id.fragment_container, firstFragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun navigateToNextFragment(position: Int) {
        when (position) {
            1 -> {
                val secondFragment = OnBoardingSecondFragment()
                supportFragmentManager.beginTransaction().apply {
                    setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    replace(R.id.fragment_container, secondFragment)
                    addToBackStack(null)
                    commit()
                }
            }
            2 -> {
                val thirdFragment = OnBoardingThirdFragment()
                supportFragmentManager.beginTransaction().apply {
                    setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    replace(R.id.fragment_container, thirdFragment)
                    addToBackStack(null)
                    commit()
                }
            }
            3 -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }
        }
    }
}