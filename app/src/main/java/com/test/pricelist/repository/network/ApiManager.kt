package com.test.pricelist.repository.network

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiManager {
    //to handle Api calls

    private var apiInterface: ApiInterface? = null

    private fun createApiInterface() {

        val client = OkHttpClient.Builder()
                //to increase timeout as socket gets timedOut
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(NetworkURL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        apiInterface = retrofit.create(ApiInterface::class.java)
    }

    fun getApiInterface(): ApiInterface? {
        if (apiInterface == null)
            createApiInterface()

        return apiInterface

    }
}

