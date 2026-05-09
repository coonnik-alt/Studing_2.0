package com.example.sqlite_room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel (private val userDao: UserDao) : ViewModel() {



    val allUser: StateFlow<List<User>> =
        userDao.getAll()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )


    fun addUserFromInput(name: String) {
        val trimmed = name.trim()
        if (trimmed.isBlank()) return

        viewModelScope.launch {
            userDao.insert(User(name = trimmed))
        }
    }

    fun deleteAllUsers(){
        viewModelScope.launch {
            userDao.deleteAll()
        }
    }

}

