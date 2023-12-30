package com.cious.learnhub.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cious.learnhub.R
import com.cious.learnhub.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel : MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNav()
    }

    private fun setupBottomNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)
        if (!viewModel.isLogin){
            binding.navView.menu.findItem(R.id.navigation_my_class).isVisible = false
            binding.navView.menu.findItem(R.id.navigation_notifications).isVisible = false
        } else {
            binding.navView.menu.findItem(R.id.navigation_profile).isVisible = true
            binding.navView.menu.findItem(R.id.navigation_my_class).isVisible = true
            binding.navView.menu.findItem(R.id.navigation_notifications).isVisible = true
        }
    }
}