package com.udacity.asteroidradar.worker

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.data.local.AsteroidDatabase
import com.udacity.asteroidradar.data.local.DatabaseHelperImpl
import com.udacity.asteroidradar.data.remote.ApiHelperImpl
import com.udacity.asteroidradar.data.remote.RetrofitClient
import com.udacity.asteroidradar.data.remote.parseAsteroidsJsonResult
import com.udacity.asteroidradar.repository.AsteroidRepo
import com.udacity.asteroidradar.utils.Constants.TODAY
import com.udacity.asteroidradar.utils.extensions.getDates
import org.json.JSONObject
import retrofit2.HttpException

class DataWork(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {


    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun doWork(): Result {
        val repository = AsteroidRepo(
            ApiHelperImpl(RetrofitClient.getApiService()), DatabaseHelperImpl(
                AsteroidDatabase.getInstance(applicationContext))
        )

        return try {
           val asteroids = parseAsteroidsJsonResult(JSONObject(repository.getAsteroidsAPI().data!!))
            repository.insertAsteroid(asteroids)
            repository.deletePreviousDayAsteroids(getDates()[TODAY]!!)
            Log.d("testW","asteroids $asteroids")
            Result.success()
        } catch (exception: HttpException) {
            Log.d("testW","exception ${exception.message()}")
            Result.retry()

        }
    }

}