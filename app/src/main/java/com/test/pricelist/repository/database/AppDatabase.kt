package com.test.pricelist.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.pricelist.models.Results


@Database(entities = [Results.RecordData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataDao(): DataDao
}