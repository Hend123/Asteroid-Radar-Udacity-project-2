package com.udacity.asteroidradar.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.presentation.viewmodel.MainViewModel
import com.udacity.asteroidradar.repository.AsteroidRepo

@Suppress("UNCHECKED_CAST")
class VMF(private val asteroidRepo: AsteroidRepo) :
    ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) return MainViewModel(asteroidRepo) as T
        throw IllegalArgumentException("Unknown class name")
    }
}