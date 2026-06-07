package com.example.test_application

import android.R.attr.text
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.test_application.DataBase.AppDataBase
import com.example.test_application.DataBase.FavoriteCourseDao
import com.example.test_application.DataBase.FavoriteCourseEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.String

@RunWith(AndroidJUnit4::class)
class FavoriteCourseDaoTest {

    private lateinit var database : AppDataBase
    private lateinit var dao : FavoriteCourseDao

    @Before
    fun setup(){

        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDataBase::class.java
        ).allowMainThreadQueries()
            .build()

        dao = database.favoriteCourseDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertCourse_CourseSavedInDatabase() = runTest {

        val course = FavoriteCourseEntity(
            id = 1 ,
            title = "Android Course",
            text = "test",
            price =  "test",
            rate = "test",
            startDate = "test",
            publishDate = "test"
        )

        dao.addToFavorites(course)

        val result = dao.getAllFavorites()

        assertEquals(1, result.size)
        assertEquals(course, result[0])
    }
}