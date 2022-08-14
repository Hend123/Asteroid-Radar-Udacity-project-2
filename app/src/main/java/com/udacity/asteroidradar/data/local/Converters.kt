package com.udacity.asteroidradar.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.udacity.asteroidradar.pojo.Asteroid
import java.lang.reflect.Type

class Converters {
    @TypeConverter
    fun fromListAsteroidToString(listAsteroid: List<Asteroid>) =
         Gson().toJson(listAsteroid)


    @TypeConverter
    fun fromStringToListAsteroid(stringAsteroid: String): List<Asteroid> {
        val listType: Type = object : TypeToken<List<Asteroid>>() {}.type
        return Gson().fromJson(stringAsteroid, listType)
    }
}