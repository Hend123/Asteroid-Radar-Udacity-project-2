package com.udacity.asteroidradar.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.udacity.asteroidradar.data.remote.parseAsteroidsJsonResult
import com.udacity.asteroidradar.pojo.Asteroid
import com.udacity.asteroidradar.pojo.PictureOfDay
import com.udacity.asteroidradar.repository.AsteroidRepo
import com.udacity.asteroidradar.utils.Constants
import com.udacity.asteroidradar.utils.Constants.LAST_DAY
import com.udacity.asteroidradar.utils.Constants.TODAY
import com.udacity.asteroidradar.utils.Resource
import com.udacity.asteroidradar.utils.extensions.getDates
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
    var asteroids: LiveData<List<Asteroid>> = MutableLiveData()
    val mediatorLiveData = MediatorLiveData<List<Asteroid>>()

    val picToday = MutableLiveData(PictureOfDay())


    init {
        getAsteroidsAPI()
        getPictureOfDay()
        getAsteroid()
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
    fun getAsteroid(){
        asteroids =  asteroidRepo.getAsteroids()
        mediatorLiveData.addSource(asteroidRepo.getAsteroids()
        ) { t -> mediatorLiveData.value = t }

    }
    fun getAsteroidToday(){
        val dateToday = getDates()[TODAY]
        Log.d("test","dateToday $dateToday")
       // asteroids =  asteroidRepo.getAsteroidToday(dateToday!!)
        mediatorLiveData.addSource(asteroidRepo.getAsteroidToday(dateToday!!)
        ) { t -> mediatorLiveData.value = t }
        Log.d("test","getAsteroidToday ${asteroids.value}")


    }
    fun getAsteroidsWeek(){
        val dates = getDates()
        asteroids =  asteroidRepo.getAsteroidsWeek(dates[TODAY]!!,dates[LAST_DAY]!!)
        mediatorLiveData.addSource(asteroidRepo.getAsteroidsWeek(dates[TODAY]!!,dates[LAST_DAY]!!)
        ) { t -> mediatorLiveData.value = t }
    }
}

