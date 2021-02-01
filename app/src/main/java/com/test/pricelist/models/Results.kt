package com.test.pricelist.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class  Results(
    @SerializedName("records")
    val results: List<RecordData>
) {
    @Entity(tableName = "pricedata")
    data class RecordData
        (
        @PrimaryKey(autoGenerate = true) var uniqueID: Int,
        @ColumnInfo(name = "timestamp") @SerializedName("timestamp") val timestamp: String,
        @ColumnInfo(name = "state") @SerializedName("state") val state: String,
        @ColumnInfo(name = "district") @SerializedName("district") val district: String,
        @ColumnInfo(name = "market") @SerializedName("market") val market: String,
        @ColumnInfo(name = "commodity") @SerializedName("commodity") val commodity: String,
        @ColumnInfo(name = "variety") @SerializedName("variety") val variety: String,
        @ColumnInfo(name = "arrival_date") @SerializedName("arrival_date") val arrival_date: String,
        @ColumnInfo(name = "min_price") @SerializedName("min_price") val min_price: String,
        @ColumnInfo(name = "max_price") @SerializedName("max_price") val max_price: String,
        @ColumnInfo(name = "modal_price") @SerializedName("modal_price") val modal_price: String
    )
}