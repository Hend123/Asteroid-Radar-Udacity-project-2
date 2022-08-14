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
    // we'll use this function in all
    // repos to handle api errors.
    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {
        var result: Resource<T>
        withContext(Dispatchers.IO) {
            result = Resource.Loading()
            try {

                // Here we are calling api lambda
                // function that will return response
                // wrapped in Retrofit's Response class
               val response = apiToBeCalled()

                if (response.isSuccessful) {
                    // In case of success response we
                    // are returning Resource.Success object
                    // by passing our data in it.
//                    val s: T = response.body()!!
//                   val t = parseAsteroidsJsonResult(JSONObject( response.body() as String)) as T
                  result = Resource.Success(data = response.body()!!)
                } else {
                    // parsing api's own custom json error
                    // response in ExampleErrorResponse pojo
                    val errorResponse: ExampleErrorResponse? =
                        convertErrorBody(response.errorBody())
                    // Simply returning api's own failure message
                    Log.d("test", "error1 $response.errorBody()")
                    Log.d("test", "error2 $errorResponse")
                  result =  Resource.Error(
                        errorMessage = errorResponse?.failureMessage ?: "Something went wrong"
                    )
                }

            } catch (e: HttpException) {
                // Returning HttpException's message
                // wrapped in Resource.Error
                Log.d("test", "error3 ${e.message()}")
               result = Resource.Error(errorMessage = e.message ?: "Something went wrong")
            } catch (e: IOException) {
                // Returning no internet message
                // wrapped in Resource.Error
                Log.d("test", "error4 ${e.message}")
             result =   Resource.Error("Please check your network connection")
            } catch (e: Exception) {
                // Returning 'Something went wrong' in case
                // of unknown error wrapped in Resource.Error
                Log.d("test", "error5 ${e.message}")
             result =   Resource.Error(errorMessage = "Something went wrong")
            }
        }
        return result
    }

    // If you don't wanna handle api's own
    // custom error response then ignore this function
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