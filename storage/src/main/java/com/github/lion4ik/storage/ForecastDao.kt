package com.github.lion4ik.storage

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.github.lion4ik.storage.model.ForecastDbModel

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForecast(forecastDbModel: ForecastDbModel)

    @Query("SELECT * FROM forecast")
    fun getForecasts(): List<ForecastDbModel>
}