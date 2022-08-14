package com.udacity.asteroidradar.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.pojo.Asteroid

@Dao
interface AsteroidDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)

    suspend fun insertAsteroid(asteroids: ArrayList<Asteroid>)

    @Query("select * from asteroid_table")
    fun getAsteroid(): LiveData<List<Asteroid>>
}