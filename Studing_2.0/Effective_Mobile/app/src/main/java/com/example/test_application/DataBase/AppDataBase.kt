package com.example.test_application.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [FavoriteCourseEntity::class],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun favoriteCourseDao(): FavoriteCourseDao

    companion object{

        @Volatile
        private var INSTANCE : AppDataBase? = null

        fun getDatabase (context : Context) : AppDataBase {

        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "courses_database"
            ).build()

            INSTANCE = instance
            instance
        }

        }

    }

}