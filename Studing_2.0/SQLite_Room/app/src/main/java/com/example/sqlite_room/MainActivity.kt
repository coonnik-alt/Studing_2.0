package com.example.sqlite_room

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputBinding
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.sqlite_room.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val userDao = (application as App).db.userDao()

                @Suppress("UNCHECKED_CAST")
                return MainViewModel(userDao) as T
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        super.onCreate(savedInstanceState)
        setContentView(view)

        binding.deleteButton.setOnClickListener {
            viewModel.deleteAllUsers()
        }

        binding.saveButton.setOnClickListener {
            addName()
        }

        observeUsers()

        }

    private fun observeUsers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allUser.collect { users ->
                    // Например, выводим имена построчно в TextView
                    binding.Text.text =
                        users.joinToString(separator = "\n") { it.name }
                }
            }
        }
    }


    fun addName (){
        val name = binding.textInputLayout.editText?.text?.toString().orEmpty()
        viewModel.addUserFromInput(name)
        binding.textInputLayout.editText?.text?.clear()
        Toast.makeText(this, "Имя добавлено", Toast.LENGTH_SHORT).show()
    }

    }
