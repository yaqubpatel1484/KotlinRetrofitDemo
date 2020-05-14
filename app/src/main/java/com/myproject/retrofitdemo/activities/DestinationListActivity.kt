package com.smartherd.globofly.activities

import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.WindowManager
import com.myproject.retrofitdemo.R
import com.myproject.retrofitdemo.models.Staff
import com.myproject.retrofitdemo.services.DestinationService
import com.myproject.retrofitdemo.services.ServiceBuilder

import com.smartherd.globofly.helpers.DestinationAdapter
import com.smartherd.globofly.helpers.SampleData
import com.smartherd.globofly.models.Destination
import kotlinx.android.synthetic.main.activity_destiny_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.HashMap

class DestinationListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_destiny_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        fab.setOnClickListener {
            val intent = Intent(this@DestinationListActivity, DestinationCreateActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        loadDestinations()
    }

    private fun loadDestinations() {

        // To be replaced by retrofit code
        //destiny_recycler_view.adapter = DestinationAdapter(SampleData.DESTINATIONS)

        val destinationService = ServiceBuilder.buildService(DestinationService::class.java)

        /*
        for basic call to fetch all list
         fun getDestinationList(): Call<List<Staff>>
       */

        val requestCall = destinationService.getDestinationList()  //default
       // val requestCall = destinationService.getDestinationList("EN")  // passing dynamic header parameter

        /*
        requestCall.cancel() // cancel() use to cancel the request..when we cancel request it directly execute onFailure()
        // isCanceled is use to know request is cancel or not by returning boolean value
        */

        /*Patch 1
        val requestCall = destinationService.getDestinationList("https://sparshims.com/sparshims_sales/get_staff.php")
         fun getDestinationList(@Url url: String): Call<List<Staff>>
         */

        /*
        Patch 2
       Path Parameter
       for passing path parameter to fetch record which id is 2
           val requestCall = destinationService.getDestinationList(2)
       fun getDetails1(@Path("id") id: Int): Call<Staff>
       */

        /*
        Patch 3.1
        Query Parameter:(Single parameter)
        for passing query parameter to fetch record which name is "Yaqub Patel"
            val requestCall = destinationService.getDestinationList("Yaqub Patel")
        fun getDestinationList(@Query("name") name: String ): Call<List<Staff>>
        */

        /*
        Patch 3.2
         Query Parameter:(null parameter)
        when we pass null as query parameter retrofit ignores parameter argument and simply pass list of all record
        means val requestCall = destinationService.getDestinationList(null) is same as
        fun getDestinationList(): Call<List<Staff>>
        fun getDestinationList(@Query("name") name: String? ): Call<List<Staff>>
        */

        /*
        patch 4
         Query Parameter: (Multiple parameter)
      for passing query parameter to fetch record which name is "Yaqub Patel"
          val requestCall = destinationService.getDestinationList("Yaqub Patel","2")
         above will return list of 2 records which have name "Yaqub Patel"
      fun getDestinationList(@Query("name") name: String?, @Query("count") count: String? ): Call<List<Staff>>
      */


        /*
        patch 5
     QueryMap example:
     val filter  = HashMap<String,String>()
      filter.put("Name","Yaqub Patel") //can be rewrite as filter["Name"] = "Yaqub Patel"
      filter.put("count","2")            // filter["count"] = "2"
      val requestCall = destinationService.getDestinationList(filter)
*/

        /*
        patch 6
      Path Query Parameter:
      for passing query parameter to fetch 2 record which id= 1 and name is "Yaqub Patel"
          val requestCall = destinationService.getDestinationList(1,"Yaqub Patel","2")
      fun getDestinationList(@Path("id") id: Int,@Query("name") name: String?, @Query("count") count: Int): Call<List<Staff>>
      */


        /*
        requestCall.enqueue() for Asynchronous call in background thread
        requestCall.execute() for Synchronous call in main thread
        request.cancel() for cancel the request.
        request.isExecute() return true if call is executed of enqueued
         */
        requestCall.enqueue(object : Callback<List<Staff>> {

            override fun onResponse(call: Call<List<Staff>>, response: Response<List<Staff>>) {

                if (response.isSuccessful) {
                    val staffList: List<Staff> = response.body()!!
                    destiny_recycler_view.adapter = DestinationAdapter(staffList)
                } else {
                    Log.e("TAG", "failure")

                }
            }

            override fun onFailure(call: Call<List<Staff>>, t: Throwable) {
                Log.e("TAG", t.toString())
            }

        })

    }


}
