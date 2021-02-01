package com.test.pricelist.repository

import androidx.recyclerview.widget.DiffUtil
import com.test.pricelist.models.Results

class DiffUtilCallBack : DiffUtil.ItemCallback<Results.RecordData>() {
    override fun areItemsTheSame(oldItem: Results.RecordData, newItem: Results.RecordData): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Results.RecordData, newItem: Results.RecordData): Boolean = oldItem == newItem
}