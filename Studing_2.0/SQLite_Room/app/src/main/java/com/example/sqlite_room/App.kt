package com.example.sqlite_room

import android.app.Application
import androidx.room.Room

class App : Application() {

    lateinit var db: AppDataBase
        private set

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,
            "db"
        ).build()
    }
}
