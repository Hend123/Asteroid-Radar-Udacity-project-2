package com.udacity.asteroidradar.data.remote

import com.udacity.asteroidradar.pojo.PictureOfDay
import retrofit2.Response


class ApiHelperImpl(private val apiService: ApiService): ApiHelper {
    override suspend fun getAsteroids(
        startDate: String,
        endDate: String,
        apiKey: String
    ): Response<String> = apiService.getAsteroids(startDate, endDate, apiKey)

    override suspend fun getPictureOfDay(apiKey: String): Response<PictureOfDay> =
        apiService.getPictureOfDay(apiKey)

}