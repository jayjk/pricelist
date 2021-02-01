package com.test.pricelist

import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.AbsListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.pricelist.adapter.PriceListAdapter
import com.test.pricelist.models.Results
import com.test.pricelist.viewmodels.DataViewModel
import kotlinx.coroutines.Job
import java.util.ArrayList

class MainActivity : AppCompatActivity() {


    private var mDataViewModel: DataViewModel? = null

    internal var isScrolling = false

    private var dialog: ProgressDialog? = null

    lateinit var rv_price_list: RecyclerView
    internal var priceListAdapter: PriceListAdapter? = null

    var currentItem: Int = 0
    var totalItems:Int = 0
    var scrollItems:Int = 0
    var pageNumber = 0

    lateinit var pb: ProgressBar

    internal var searchList = ArrayList<Results.RecordData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        setupRecyclerView()
    }


    private fun init() {
        rv_price_list = findViewById(R.id.recycler_view)

        dialog = ProgressDialog(this@MainActivity)

        pb = findViewById(R.id.pb)


        mDataViewModel = ViewModelProviders.of(this).get(DataViewModel::class.java!!)
        mDataViewModel!!.init(applicationContext)

        getAllData(0)


        rv_price_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    isScrolling = true
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if((applicationContext!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                        .activeNetworkInfo?.isConnected == true){

                    currentItem = recyclerView.layoutManager!!.childCount
                    totalItems = recyclerView.layoutManager!!.itemCount
                    scrollItems =
                        (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()


                    // To identify user has finished scrolling and fetch data for next page

                    if (isScrolling && currentItem + scrollItems == totalItems) {
                        pageNumber += 10
                        isScrolling = false
                        if (pageNumber < 40) {
                            pb.setVisibility(View.VISIBLE)

                            getAllData(pageNumber)

                        }
                    }

                }

            }
        })

    }

    private fun getAllData(i: Int) {
        mDataViewModel!!.getMasterData(i)!!.observe(this@MainActivity,
            Observer<List<Results.RecordData>> { data ->
                pb.setVisibility(View.GONE)
                dialog!!.dismiss()

                if (data != null) {
                    searchList.addAll(data)
                } else
                    Toast.makeText(
                        this@MainActivity,
                        resources.getString(R.string.app_name),
                        Toast.LENGTH_SHORT
                    ).show()

                priceListAdapter!!.notifyDataSetChanged()
            })
    }


    private fun setupRecyclerView() {
        if (priceListAdapter == null) {
            priceListAdapter = PriceListAdapter(this@MainActivity,searchList)
            rv_price_list.layoutManager = LinearLayoutManager(this)
            rv_price_list.adapter = priceListAdapter
            rv_price_list.itemAnimator = DefaultItemAnimator()
            rv_price_list.isNestedScrollingEnabled = true
        } else {
            priceListAdapter!!.notifyDataSetChanged()
        }
    }

    private var searchView: SearchView? = null
    override fun onCreateOptionsMenu(m: Menu): Boolean {

        menuInflater.inflate(R.menu.menu, m)

        val searchViewMenuItem = m.findItem(R.id.search)
        searchView = searchViewMenuItem.actionView as SearchView

        searchView!!.setQueryHint("Search By Product/Market/District")

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                searchList.clear()
                mDataViewModel!!.getFilterData(newText)!!.observe(this@MainActivity,
                    Observer<List<Results.RecordData>> { data ->
                        if(data!=null)
                            searchList.addAll(data)

                        priceListAdapter!!.notifyDataSetChanged()
                    })


                return false
            }
        })

        return true
    }

}
