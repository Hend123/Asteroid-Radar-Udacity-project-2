package com.udacity.asteroidradar.data.remote

import com.udacity.asteroidradar.pojo.Asteroid
import com.udacity.asteroidradar.utils.Constants
import com.udacity.asteroidradar.pojo.PictureOfDay
import kotlinx.coroutines.Deferred
import retrofit2.Response


interface ApiHelper {
    suspend fun getAsteroids(
        startDate: String,
        endDate: String,
        apiKey: String = Constants.API_KEY
    ): Response<String>


    suspend fun getPictureOfDayAsync(
        apiKey: String = Constants.API_KEY
    ): Deferred<PictureOfDay>
}