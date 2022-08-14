package com.udacity.asteroidradar.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.data.local.DatabaseHelper
import com.udacity.asteroidradar.data.remote.ApiHelper
import com.udacity.asteroidradar.pojo.Asteroid
import com.udacity.asteroidradar.utils.Constants.LAST_DAY
import com.udacity.asteroidradar.utils.Constants.TODAY
import com.udacity.asteroidradar.utils.Resource
import com.udacity.asteroidradar.utils.extensions.getDates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsteroidRepo(private val apiHelper: ApiHelper, private val databaseHelper: DatabaseHelper) :
    BaseRepo() {
    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun getAsteroidsAPI(): Resource<String> {
        val dates = getDates()
        return safeApiCall { apiHelper.getAsteroids(dates[TODAY]!!, dates[LAST_DAY]!!) }
    }

    suspend fun insertAsteroid(asteroids: ArrayList<Asteroid>) {
        withContext(Dispatchers.IO) {
            databaseHelper.insertAsteroid(asteroids)
        }
    }

    fun getAsteroids() :LiveData<List<Asteroid>>{
        val c = databaseHelper.getAsteroid()
        Log.d("test","c ${c.value}")
        return c
    }


}