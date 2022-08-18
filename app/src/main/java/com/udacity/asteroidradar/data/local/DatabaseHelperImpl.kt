package com.udacity.asteroidradar.data.local

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.pojo.Asteroid

class DatabaseHelperImpl(private val asteroidDatabase: AsteroidDatabase) : DatabaseHelper {
    override suspend fun insertAsteroid(asteroids: ArrayList<Asteroid>) =
        asteroidDatabase.asteroidDao().insertAsteroid(asteroids)


    override fun getAsteroid(): LiveData<List<Asteroid>> =
        asteroidDatabase.asteroidDao().getAsteroid()

    override fun getAsteroidToday(dateToday: String): LiveData<List<Asteroid>> =
        asteroidDatabase.asteroidDao().getAsteroidToday(dateToday)

    override fun getAsteroidsWeek(
        startDate: String,
        endDate: String
    ): LiveData<List<Asteroid>> = asteroidDatabase.asteroidDao().getAsteroidsWeek(startDate, endDate)

    override fun deletePreviousDayAsteroids(today: String): Int =
        asteroidDatabase.asteroidDao().deletePreviousDayAsteroids(today)
}