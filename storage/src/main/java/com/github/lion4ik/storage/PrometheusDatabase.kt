package com.github.lion4ik.storage

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.github.lion4ik.storage.model.ForecastDbModel

@Database(
    entities = [
        ForecastDbModel::class
    ],
    version = PrometheusDatabase.DB_VERSION,
    exportSchema = true
)
abstract class PrometheusDatabase : RoomDatabase() {

    companion object {
        const val DB_FILE_NAME = "prometheus.db"
        const val DB_VERSION = 1
    }

    abstract fun getForecastDao(): ForecastDao

}
