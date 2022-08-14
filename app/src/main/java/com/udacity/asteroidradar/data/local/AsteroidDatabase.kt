package com.udacity.asteroidradar.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.udacity.asteroidradar.pojo.Asteroid

@Database(
    entities = [Asteroid::class],
    version = 1,
    exportSchema = false
)
//@TypeConverters(Converters::class)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract fun asteroidDao(): AsteroidDao


    companion object {
        @Volatile
        private var instance: AsteroidDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AsteroidDatabase {
            instance
                ?: synchronized(this) {
                    instance = Room.databaseBuilder(
                        context,
                        AsteroidDatabase::class.java, "asteroid_database"
                    ).build()
                }
            return instance!!
        }
    }
}