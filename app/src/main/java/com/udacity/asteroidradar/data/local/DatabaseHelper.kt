package com.udacity.asteroidradar.data.local

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.pojo.Asteroid

interface DatabaseHelper {
    suspend fun insertAsteroid(asteroids: ArrayList<Asteroid>)

    fun getAsteroid(): LiveData<List<Asteroid>>
    fun getAsteroidToday(dateToday: String): LiveData<List<Asteroid>>
    fun getAsteroidsWeek(startDate: String, endDate: String): LiveData<List<Asteroid>>
    fun deletePreviousDayAsteroids(today: String): Int

}