package com.example.test_application.ui.VM

import android.content.SharedPreferences
import android.util.Log.e
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_application.Data.CourseDto
import com.example.test_application.Data.CoursesApi
import com.example.test_application.Data.RetrofitInstance
import com.example.test_application.DataBase.FavoriteCourseDao
import com.example.test_application.DataBase.FavoriteCourseEntity
import kotlinx.coroutines.launch

class CoursesViewModel(
    private val api: CoursesApi,
    private val favoriteCourseDao: FavoriteCourseDao,
    private val prefs: SharedPreferences
) : ViewModel() {

    var courses by mutableStateOf<List<CourseDto>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorText by mutableStateOf<String?>(null)
        private set

    init {
        loadCourses()
    }

    fun sortByPublishDate() {
        courses = courses.sortedByDescending { it.publishDate }
    }

    fun toggleLike(course: CourseDto) {
        viewModelScope.launch {
            val courseId = course.id ?: return@launch

            val isFavorite = favoriteCourseDao.isFavorite(courseId)

            if (isFavorite) {
                favoriteCourseDao.deleteById(courseId)
            } else {
                favoriteCourseDao.addToFavorites(
                    FavoriteCourseEntity(
                        id = courseId,
                        title = course.title ?: "",
                        text = course.text ?: "",
                        price = course.price ?: "",
                        rate = course.rate ?: "",
                        startDate = course.startDate ?: "",
                        publishDate = course.publishDate ?: ""
                    )
                )
            }

            courses = courses.map {
                if (it.id == courseId) {
                    it.copy(hasLike = !isFavorite)
                } else {
                    it
                }
            }
        }
    }

    fun refreshFavoriteFlags() {
        viewModelScope.launch {
            courses = courses.map { course ->
                val courseId = course.id

                val isFavorite = if (courseId != null) {
                    favoriteCourseDao.isFavorite(courseId)
                } else {
                    false
                }

                course.copy(hasLike = isFavorite)
            }
        }
    }
    private fun loadCourses() {
        viewModelScope.launch {
            try {
                isLoading = true
                errorText = null

                val apiCourses = api.getCourses().courses

                val alreadySeeded = prefs.getBoolean("favorites_seeded", false)

                if (!alreadySeeded) {
                    apiCourses.forEach { course ->
                        if (course.hasLike == true && course.id != null) {
                            favoriteCourseDao.addToFavorites(
                                FavoriteCourseEntity(
                                    id = course.id,
                                    title = course.title ?: "",
                                    text = course.text ?: "",
                                    price = course.price ?: "",
                                    rate = course.rate ?: "",
                                    startDate = course.startDate ?: "",
                                    publishDate = course.publishDate ?: ""
                                )
                            )
                        }
                    }

                    prefs.edit()
                        .putBoolean("favorites_seeded", true)
                        .apply()
                }

                courses = apiCourses.map { course ->
                    val courseId = course.id

                    val isFavoriteFromDb = if (courseId != null) {
                        favoriteCourseDao.isFavorite(courseId)
                    } else {
                        false
                    }

                    course.copy(hasLike = isFavoriteFromDb)
                }

            } catch (e: Exception) {
                errorText = "Ошибка загрузки"

            } finally {
                isLoading = false
            }
        }
    }
}