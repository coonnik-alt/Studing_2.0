package com.example.practwork15

import android.R.attr.id
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.Contacts.SettingsColumns.KEY

class DataBaseHelper (context : Context) :
     SQLiteOpenHelper (context, "photos.db" , null , 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
        CREATE TABLE photos (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            uri TEXT,
            date TEXT
        )
    """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    fun insertItem(uri: String, date: String) {

        val db = writableDatabase

        val values = ContentValues().apply {
            put("uri", uri)
            put("date", date)
        }

        db.insert("photos", null, values)

    }

    fun getAllItems(): List<Item> {
        val list = mutableListOf<Item>()
        val db = readableDatabase

        val cursor = db.rawQuery("SELECT * FROM photos ORDER BY id DESC", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val uri = cursor.getString(cursor.getColumnIndexOrThrow("uri"))
                val date = cursor.getString(cursor.getColumnIndexOrThrow("date"))

                list.add(Item(id, uri, date))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }
}