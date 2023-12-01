package com.cious.learnhub.Nofitcations.model

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cious.learnhub.R
import com.cious.learnhub.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNav)

        val fragment1 = BerandaFragment()
        val fragment2 = NotificationFragment()
        val fragment3 = KelasFragment()
        val fragment4 = KursusFragment()
        val fragment5 = AkunFragment()

        if (savedInstanceState == null) {
            replaceFragment(fragment1)
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_item_1 -> replaceFragment(fragment1)
                R.id.menu_item_2 -> replaceFragment(fragment2)
                R.id.menu_item_3 -> replaceFragment(fragment3)
                R.id.menu_item_4 -> replaceFragment(fragment4)
                R.id.menu_item_5 -> replaceFragment(fragment5)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}