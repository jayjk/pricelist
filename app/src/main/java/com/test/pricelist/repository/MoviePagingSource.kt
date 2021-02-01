package com.test.pricelist.repository

import android.content.Context
import androidx.paging.PagingSource
import com.test.pricelist.models.Results
import com.test.pricelist.repository.network.ApiManager.getApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviePagingSource(
  val context: Context
) : PagingSource<Int, Results.RecordData>() {

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results.RecordData> {
    try {
      // Start refresh at page 1 if undefined.

      val nextPage = params.key ?: 0

      lateinit var localData : List<Results.RecordData>

      try {

        getApiInterface()?.getData(nextPage, nextPage+10)
          ?.enqueue(object : Callback<Results> {

            override fun onResponse(call: Call<Results>, response: Response<Results>) {
              try {
                if (response.isSuccessful()) {
                  val res = response.body()
                  localData = res!!.results
                }
              } catch (e: Exception) {
                e.printStackTrace()
              }

            }

            override fun onFailure(call: Call<Results>, t: Throwable) {
              t.printStackTrace()
            }
          }
          )
      } catch (e: Exception) {
        e.printStackTrace()
      }

      return LoadResult.Page(
        data = localData ,
        prevKey = if (nextPage == 10) null else nextPage - 1,
        nextKey = nextPage + 1
      )
    } catch (e: Exception) {
      return LoadResult.Error(e)
    }
  }
}