package com.example.firebasetest

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebasetest.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics

private lateinit var binding : ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Firebase.crashlytics.setCrashlyticsCollectionEnabled(true)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        binding.button.setOnClickListener {
            throw RuntimeException("Test Crash")
        }

        }
    }
