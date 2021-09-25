package com.ruben.covidstats.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ruben.covidstats.R
import com.ruben.covidstats.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
    }

    private fun setupNavigation() {
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_country ->{
                    supportFragmentManager.beginTransaction().replace(R.id.flOptions,CountryFragment.newInstance()).commit()
                }
                R.id.action_region -> {
                    supportFragmentManager.beginTransaction().replace(R.id.flOptions,RegionFragment.newInstance()).commit()
                }
            }
            true
        }
    }

}