package com.test.pricelist.repository

import android.content.Context

import androidx.lifecycle.MutableLiveData

import com.test.pricelist.models.Results
import com.test.pricelist.repository.database.DatabaseClient
import com.test.pricelist.repository.network.ApiInterface
import com.test.pricelist.repository.network.ApiManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataRepository {




    private val api: ApiInterface? = ApiManager.getApiInterface()


    fun getMasterDatas(pageNumber: Int): MutableLiveData<List<Results.RecordData>> {

        val data = MutableLiveData<List<Results.RecordData>>()

        try {

            api?.getData(pageNumber, +10)
                ?.enqueue(object : Callback<Results> {

                    override fun onResponse(call: Call<Results>, response: Response<Results>) {
                        try {
                            if (response.isSuccessful) {
                                insertInToDB(response.body()!!.results,pageNumber)

                                data.value = response.body()!!.results
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            data.setValue(null)
                        }

                    }

                    override fun onFailure(call: Call<Results>, t: Throwable) {
                        t.printStackTrace()
                        data.setValue(null)
                    }
                }
                )


        } catch (e: Exception) {
            e.printStackTrace()
        }

        return data
    }

    fun insertInToDB(
        data: List<Results.RecordData>,
        pageNumber: Int
    ){

        data.size


        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            try {
                if (pageNumber == 0){
                    DatabaseClient.getInstance(context!!.applicationContext).appDatabase.dataDao().deleteAllData()
                }

                DatabaseClient.getInstance(context!!.applicationContext).appDatabase.dataDao().insert(data)

            } catch (throwable: Throwable) {

            }
        }

    }


    companion object {
        private var context: Context? = null
        private var instance: DataRepository? = null

        fun  getInstance(applicationContext: Context): DataRepository {
            context = applicationContext
            if (instance == null) {
                instance = DataRepository()
            }
            return instance as DataRepository
        }
    }
}