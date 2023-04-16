package com.example.dvs.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.dvs.R
import com.example.dvs.databinding.ActivityMainBinding
import com.example.dvs.ui.chat.ChatFragment
import com.example.dvs.ui.profile.ProfileFragment
import com.example.dvs.ui.settings.SettingsFragment
import com.google.android.material.navigation.NavigationBarView

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupAction()
    }

    private fun setupAction() {

        val bottomNav = binding.bottomNavigation

        val fragment = HomeFragment.newInstance("test1","test2")
        bottomNav.setOnItemSelectedListener(menuItemSelected)
        addFragment(fragment)

    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_home, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    private val menuItemSelected = NavigationBarView.OnItemSelectedListener  { item ->
        when (item.itemId) {
            R.id.menu_home ->{
                val fragment = HomeFragment.newInstance("test1","test2")
                addFragment(fragment)
                return@OnItemSelectedListener  true
            }
            R.id.menu_chat ->{
                val fragment = ChatFragment.newInstance("test1","test2")
                addFragment(fragment)
                return@OnItemSelectedListener true
            }
            R.id.menu_profile ->{
                val fragment = ProfileFragment.newInstance("test1","test2")
                addFragment(fragment)
                return@OnItemSelectedListener true
            }
            R.id.menu_settings ->{
                val fragment = SettingsFragment.newInstance("test1","test2")
                addFragment(fragment)
                return@OnItemSelectedListener true
            }
        }
        false
    }

}