package com.udacity.asteroidradar.data.local

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.pojo.Asteroid

interface DatabaseHelper {
    suspend fun insertAsteroid(asteroids: ArrayList<Asteroid>)

    fun getAsteroid(): LiveData<List<Asteroid>>
}