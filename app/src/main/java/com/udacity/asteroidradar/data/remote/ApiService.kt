package com.udacity.asteroidradar.data.remote


import com.udacity.asteroidradar.pojo.Asteroid
import com.udacity.asteroidradar.utils.Constants
import com.udacity.asteroidradar.pojo.PictureOfDay
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("neo/rest/v1/feed")
  suspend  fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<String>

    @GET("planetary/apod")
    fun getPictureOfDayAsync(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Deferred<PictureOfDay>
}