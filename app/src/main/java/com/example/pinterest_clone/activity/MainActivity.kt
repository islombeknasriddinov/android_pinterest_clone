package com.example.pinterest_clone.activity

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pinterest_clone.R
import com.example.pinterest_clone.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
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

    private fun setTransparentStatusBarColor(
        context: Context?,
        textColor: Int?,
        statusColor: Int?,
        lightStatus: Int?
    ) {
        window.decorView.systemUiVisibility =
            ContextCompat.getColor(context!!, textColor!!) //  set status text dark
        window.statusBarColor = ContextCompat.getColor(
            context,
            statusColor!!
        ) // set status bar color
        window.decorView.systemUiVisibility = lightStatus!!
    }
}