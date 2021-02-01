package com.test.pricelist.viewmodels

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.test.pricelist.models.Results
import com.test.pricelist.repository.DataRepository
import com.test.pricelist.repository.database.DatabaseClient


class DataViewModel : ViewModel() {


    val isConnected: Boolean
        get() {
            return (context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .activeNetworkInfo?.isConnected == true
        }

    private var mMasterData: LiveData<List<Results.RecordData>>? = null

    private var mTempMasterData: LiveData<List<Results.RecordData>>? = null

    private var mRepo: DataRepository? = null

     var context: Context? = null

    fun init(applicationContext: Context) {
        if (mMasterData != null) {
            return
        }
        context = applicationContext
        mRepo = DataRepository.getInstance(applicationContext)
        mMasterData = MutableLiveData()
    }


    fun getMasterData(pageNumber: Int): LiveData<List<Results.RecordData>>? {


        if (isConnected){
            mMasterData = null
            mMasterData = mRepo!!.getMasterDatas(pageNumber)
        }else{
            if (pageNumber == 0)
                mMasterData = DatabaseClient.getInstance(context!!.applicationContext)
                    .appDatabase.dataDao().getAllData()
            else
                mMasterData = null
        }
        return mMasterData
    }

    fun getFilterData(serText: String) : LiveData<List<Results.RecordData>>?{
        if(serText.isEmpty()){
            return mMasterData
        }else{
            mTempMasterData = mMasterData!!.map { it ->
                it.filter { it.district.toLowerCase().contains(serText) || it.market.toLowerCase().contains(serText) || it.commodity.toLowerCase().contains(serText) }
            }
            return mTempMasterData
        }
    }


}

