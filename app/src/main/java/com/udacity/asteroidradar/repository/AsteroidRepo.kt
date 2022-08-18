package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.data.local.DatabaseHelper
import com.udacity.asteroidradar.data.remote.ApiHelper
import com.udacity.asteroidradar.pojo.Asteroid
import com.udacity.asteroidradar.pojo.PictureOfDay
import com.udacity.asteroidradar.utils.Constants.LAST_DAY
import com.udacity.asteroidradar.utils.Constants.TODAY
import com.udacity.asteroidradar.utils.Resource
import com.udacity.asteroidradar.utils.extensions.getDates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsteroidRepo(private val apiHelper: ApiHelper, private val databaseHelper: DatabaseHelper) :
    BaseRepo() {
    suspend fun getAsteroidsAPI(): Resource<String> {
        val dates = getDates()
        return safeApiCall { apiHelper.getAsteroids(dates[TODAY]!!, dates[LAST_DAY]!!) }
    }

    suspend fun insertAsteroid(asteroids: ArrayList<Asteroid>) {
        withContext(Dispatchers.IO) {
            databaseHelper.insertAsteroid(asteroids)
        }
    }

    fun getAsteroids() = databaseHelper.getAsteroid()
    fun getAsteroidToday(dateToday: String) = databaseHelper.getAsteroidToday(dateToday)

    fun getAsteroidsWeek(
        startDate: String,
        endDate: String
    ) = databaseHelper.getAsteroidsWeek(startDate, endDate)

    fun deletePreviousDayAsteroids(today: String): Int {
        return databaseHelper.deletePreviousDayAsteroids(today)
    }

    suspend fun getPictureOfDay(): Resource<PictureOfDay> =
        safeApiCall { apiHelper.getPictureOfDay() }

}