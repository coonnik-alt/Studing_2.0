package com.example.restapi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.restapi.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val refrButton = binding.button
        loadUser()

        refrButton.setOnClickListener(){
            loadUser()
        }
    }
        private fun loadUser() {
            lifecycleScope.launch {
                try {
                    binding.name.text = "Loading..."

                    val response = RetrofitInstance.api.getUser()

                    val user = response.results.first()
                    binding.name.text = "${user.name.first}"
                    binding.last.text = "${user.name.last}"

                    val imageUrl = user.picture.medium
                    Glide.with(binding.image)
                        .load(imageUrl)
                        .into(binding.image)

                } catch (e: Throwable) {

                }
            }
        }
}


