package com.test.pricelist.repository.database

import android.content.Context

import androidx.room.Room

class DatabaseClient private constructor(mCtx: Context) {

    //our app database object
    val appDatabase: AppDatabase =
        Room.databaseBuilder(mCtx, AppDatabase::class.java, "PriceDataDB").build()

    companion object {
        private var mInstance: DatabaseClient? = null

        @Synchronized
        fun getInstance(mCtx: Context): DatabaseClient {
            if (mInstance == null) {
                mInstance = DatabaseClient(mCtx)
            }
            return mInstance as DatabaseClient
        }
    }
}