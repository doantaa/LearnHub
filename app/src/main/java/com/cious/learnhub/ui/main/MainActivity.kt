package com.cious.learnhub.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.cious.learnhub.R
import com.cious.learnhub.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setIntropageNav()
//        setupBottomNav()
    }

    private fun setIntropageNav() {
        val navHost = binding.navHostFragmentActivityMain.getFragment<Fragment>() as NavHostFragment

        navController = navHost.navController
        binding.navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener{_, destination, _ ->
            when(destination.id){
                R.id.navigation_home -> hideBottomNav(false)
                R.id.navigation_notifications -> hideBottomNav(false)
                R.id.navigation_my_class -> hideBottomNav(false)
                R.id.navigation_course -> hideBottomNav(false)
                R.id.navigation_profile -> hideBottomNav(false)
                else -> hideBottomNav(true)
            }
        }

        if (!viewModel.isLogin){
            binding.navView.menu.findItem(R.id.navigation_my_class).isVisible = false
            binding.navView.menu.findItem(R.id.navigation_notifications).isVisible = false
        } else {
            binding.navView.menu.findItem(R.id.navigation_profile).isVisible = true
            binding.navView.menu.findItem(R.id.navigation_my_class).isVisible = true
            binding.navView.menu.findItem(R.id.navigation_notifications).isVisible = true
        }
    }

    private fun hideBottomNav(hide: Boolean) {
        if (hide){
            binding.navView.visibility = View.GONE
        }else{
            binding.navView.visibility = View.VISIBLE
        }

    }

    private fun setupBottomNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)

    }
}