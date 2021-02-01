package com.test.pricelist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.pricelist.R
import com.test.pricelist.models.Results
import java.util.ArrayList


class PriceListAdapter(appcontext: Context, articles: ArrayList<Results.RecordData>) :
    RecyclerView.Adapter<PriceListAdapter.CandidateViewHolder>() {


      var context: Context = appcontext
      var movieData: ArrayList<Results.RecordData> = articles

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CandidateViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return CandidateViewHolder(view)
    }

    override fun onBindViewHolder(holder: CandidateViewHolder, position: Int) {
        holder.tv_product_name.text =  movieData[position].commodity

        holder.tv_price.text = "Price Range: " + movieData[position].min_price  +" - "+ movieData[position].max_price

        holder.tv_market.text = "Market: " + movieData[position].market

        holder.tv_district.text = "District: " + movieData[position].district

        holder.tv_state.text = "State: " + movieData[position].state
    }


    override fun getItemCount(): Int {
        return movieData.size
    }

    inner class CandidateViewHolder(view: View) : RecyclerView.ViewHolder(view) {

         var tv_product_name: TextView = view.findViewById(R.id.txt_product_name)
        var tv_price: TextView = view.findViewById(R.id.txt_price)
        var tv_market: TextView = view.findViewById(R.id.txt_market)
        var tv_district: TextView = view.findViewById(R.id.txt_district)
        var tv_state: TextView = view.findViewById(R.id.txt_state)

    }


}