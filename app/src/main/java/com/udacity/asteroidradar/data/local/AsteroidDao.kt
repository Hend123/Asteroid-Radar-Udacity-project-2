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

    @Query("select * from asteroid_table  ORDER BY closeApproachDate  ASC")
    fun getAsteroid(): LiveData<List<Asteroid>>

    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate >= :startDate AND closeApproachDate <= :endDate ORDER BY closeApproachDate ASC")
    fun getAsteroidsWeek(startDate: String, endDate: String): LiveData<List<Asteroid>>

    @Query("select * from asteroid_table WHERE closeApproachDate == :dateToday ORDER BY closeApproachDate  ASC")
    fun getAsteroidToday(dateToday: String): LiveData<List<Asteroid>>

    @Query("DELETE FROM asteroid_table WHERE closeApproachDate < :today")
    fun deletePreviousDayAsteroids(today: String): Int
}