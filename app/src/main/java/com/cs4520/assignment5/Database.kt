package com.cs4520.assignment5

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

object Database {
    private var _instance: DatabaseHandle? = null

    fun getInstance(context: Context): DatabaseHandle {
        return if (_instance == null) {
            val instance = Room.databaseBuilder(
                context,
                DatabaseHandle::class.java, "products"
            ).build()
            _instance = instance
            instance
        } else {
            _instance!!
        }
    }

    @Database(entities = [Product::class], version = 1)
    abstract class DatabaseHandle : RoomDatabase() {
        abstract fun productDao(): ProductDao
    }
}