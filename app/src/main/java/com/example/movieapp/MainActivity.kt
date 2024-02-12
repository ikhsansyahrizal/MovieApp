package com.example.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import com.example.movieapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        menuView()

    }

    private fun menuView() {
        binding.bottomNav.setOnItemSelectedListener { item ->
            val navController = findNavController(R.id.nav_host_fragment)
            when(item.itemId) {
                R.id.home -> {
                    navController.navigate(R.id.fragmentHome)
                    true
                }

                R.id.discover -> {
                    navController.navigate(R.id.fragmentDiscover)
                    true
                }

                R.id.collection -> {
                    navController.navigate(R.id.fragmentCollection)
                    true
                }
                else -> false
            }
        }
    }
}