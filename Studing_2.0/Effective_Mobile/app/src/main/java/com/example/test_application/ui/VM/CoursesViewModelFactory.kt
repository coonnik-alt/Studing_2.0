package com.example.test_application.ui.VM

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.test_application.Data.CoursesApi
import com.example.test_application.DataBase.FavoriteCourseDao

class CoursesViewModelFactory(
    private val api: CoursesApi,
    private val favoriteCourseDao: FavoriteCourseDao,
    private val prefs: SharedPreferences
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CoursesViewModel(api, favoriteCourseDao, prefs) as T
    }
}