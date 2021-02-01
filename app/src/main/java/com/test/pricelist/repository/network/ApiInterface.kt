package com.test.pricelist.repository.network


import com.test.pricelist.models.Results

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query


interface ApiInterface {

    @GET("9ef84268-d588-465a-a308-a864a43d0070?api-key=579b464db66ec23bdd000001eb9a38e9f735497f52f12598db0e62ef&format=json")
    fun getData(@Query("offset") offset: Int,
                @Query("limit") limit: Int): Call<Results>

}
