package com.example.pinterest_clone.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pinterest_clone.R
import com.example.pinterest_clone.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTransparentStatusBarColor(
            this,
            R.color.black,
            R.color.white,
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        )
        initViews()
    }

    private fun initViews() {
        val navController = findNavController(R.id.nav_host_fragment)
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}