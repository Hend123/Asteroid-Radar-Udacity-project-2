package com.udacity.asteroidradar.data.local

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.pojo.Asteroid

class DatabaseHelperImpl(private val asteroidDatabase: AsteroidDatabase) : DatabaseHelper {
    override suspend fun insertAsteroid(asteroids: ArrayList<Asteroid>) =
        asteroidDatabase.asteroidDao().insertAsteroid(asteroids)


    override fun getAsteroid(): LiveData<List<Asteroid>> =
        asteroidDatabase.asteroidDao().getAsteroid()
}