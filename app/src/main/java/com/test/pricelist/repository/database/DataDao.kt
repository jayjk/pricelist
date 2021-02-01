package com.test.pricelist.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

import com.test.pricelist.models.Results

@Dao
interface DataDao {

    @Query("SELECT * FROM pricedata")
     fun getAllData(): LiveData<List<Results.RecordData>>

    @Insert
    fun insert(data: List<Results.RecordData>)

    @Query("DELETE FROM pricedata")
    fun deleteAllData()


}