package com.udacity.asteroidradar.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.data.remote.parseAsteroidsJsonResult
import com.udacity.asteroidradar.pojo.Asteroid
import com.udacity.asteroidradar.pojo.PictureOfDay
import com.udacity.asteroidradar.repository.AsteroidRepo
import com.udacity.asteroidradar.utils.Resource
import kotlinx.coroutines.launch
import org.json.JSONObject

@RequiresApi(Build.VERSION_CODES.N)
class MainViewModel(private val asteroidRepo: AsteroidRepo) : ViewModel() {
    private val _asteroidsLoading = MutableLiveData(true)
    val asteroidsLoading: LiveData<Boolean> = _asteroidsLoading
    private val _asteroidsSuccess = MutableLiveData<ArrayList<Asteroid>>(ArrayList())
    val asteroidsSuccess: LiveData<ArrayList<Asteroid>> = _asteroidsSuccess
    private val _asteroidsError = MutableLiveData("")
    val asteroidsError: LiveData<String> = _asteroidsError
    val asteroids: LiveData<List<Asteroid>> = asteroidRepo.getAsteroids()
    val picToday = MutableLiveData(PictureOfDay())


    init {
        getAsteroidsAPI()
        getPictureOfDay()
    }


    private fun getAsteroidsAPI() = viewModelScope.launch {
        when (val asteroids = asteroidRepo.getAsteroidsAPI()) {
            is Resource.Loading -> {
                _asteroidsLoading.value = true
            }
            is Resource.Success -> {
                _asteroidsLoading.value = false
                _asteroidsSuccess.value = parseAsteroidsJsonResult(JSONObject(asteroids.data!!))

                asteroidRepo.insertAsteroid(_asteroidsSuccess.value!!)
            }
            is Resource.Error -> {
                _asteroidsLoading.value = false
                _asteroidsError.value = asteroids.message!!
            }
        }
    }

    private fun getPictureOfDay() = viewModelScope.launch {
        when (val _picToday = asteroidRepo.getPictureOfDay()) {
            is Resource.Loading -> {
                _asteroidsLoading.value = true
            }
            is Resource.Success -> {
                _asteroidsLoading.value = false
                picToday.value = _picToday.data
            }
            is Resource.Error -> {
                _asteroidsLoading.value = false
                _asteroidsError.value = _picToday.message
            }
        }

    }
}

