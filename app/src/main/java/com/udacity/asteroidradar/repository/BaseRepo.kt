package com.udacity.asteroidradar.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.squareup.moshi.Moshi
import com.udacity.asteroidradar.pojo.ExampleErrorResponse
import com.udacity.asteroidradar.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepo() {
    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {
        var result: Resource<T>
        withContext(Dispatchers.IO) {
            result = Resource.Loading()
            try {
               val response = apiToBeCalled()

                if (response.isSuccessful) {
                  result = Resource.Success(data = response.body()!!)
                } else {
                    val errorResponse: ExampleErrorResponse? =
                        convertErrorBody(response.errorBody())
                  result =  Resource.Error(
                        errorMessage = errorResponse?.failureMessage ?: "Something went wrong"
                    )
                }

            } catch (e: HttpException) {
               result = Resource.Error(errorMessage = e.message ?: "Something went wrong")
            } catch (e: IOException) {
             result =   Resource.Error("Please check your network connection")
            } catch (e: Exception) {
             result =   Resource.Error(errorMessage = "Something went wrong")
            }
        }
        return result
    }
    private fun convertErrorBody(errorBody: ResponseBody?): ExampleErrorResponse? {
        return try {
            errorBody?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(ExampleErrorResponse::class.java)
                moshiAdapter.fromJson(it)
            }
        } catch (exception: Exception) {
            null
        }
    }
}