package com.smartherd.globofly.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.myproject.retrofitdemo.R
import com.myproject.retrofitdemo.models.Staff
import com.myproject.retrofitdemo.services.DestinationService
import com.myproject.retrofitdemo.services.ServiceBuilder
import com.smartherd.globofly.helpers.SampleData
import com.smartherd.globofly.models.Destination
import kotlinx.android.synthetic.main.activity_destiny_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class DestinationDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destiny_detail)

        setSupportActionBar(detail_toolbar)
        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle: Bundle? = intent.extras

        if (bundle?.containsKey(ARG_ITEM_ID)!!) {

            val id = intent.getIntExtra(ARG_ITEM_ID, 0)

            loadDetails(id)

            initUpdateButton(id)

            initDeleteButton(id)
        }
    }

    private fun loadDetails(id: Int) {

        // To be replaced by retrofit code
        /*	val destination = SampleData.getDestinationById(id)

            destination?.let {
                et_city.setText(destination.city)
                et_description.setText(destination.description)
                et_country.setText(destination.country)

                collapsing_toolbar.title = destination.city
            } */


        val destinationService: DestinationService =
            ServiceBuilder.buildService(DestinationService::class.java)

        val callRequest: Call<List<Staff>> = destinationService.getDetails(id)

        callRequest.enqueue(object : Callback<List<Staff>> {

            override fun onResponse(call: Call<List<Staff>>, response: Response<List<Staff>>) {

                if (response.isSuccessful) {

                    val staff: List<Staff>? = response.body()
                    staff?.let {
                        et_name.setText(staff[0].name)
                        et_mobile.setText(staff[0].contact)
                        et_address.setText(staff[0].address)

                        collapsing_toolbar.title = staff[0].name
                    }

                } else {
                    Log.e("Error", "t.toString()")
                    Toast.makeText(this@DestinationDetailActivity, "failure", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<List<Staff>>, t: Throwable) {
                Log.e("Error", t.toString())
                Toast.makeText(this@DestinationDetailActivity, t.toString(), Toast.LENGTH_LONG)
                    .show()
            }
        })


    }

    private fun initUpdateButton(id: Int) {

        btn_update.setOnClickListener {

            val name = et_name.text.toString()
            val contact = et_mobile.text.toString()
            val address = et_address.text.toString()

            val destinationService: DestinationService =
                ServiceBuilder.buildService(DestinationService::class.java)
            val requestCall: Call<String> =
                destinationService.updateStaff(id, name, contact, address)

            requestCall.enqueue(object: Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.isSuccessful){
                        Toast.makeText(this@DestinationDetailActivity,"Success- ${response.body()}", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this@DestinationDetailActivity,"Failure", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("TAG", t.toString())
                    Toast.makeText(this@DestinationDetailActivity,t.toString(), Toast.LENGTH_LONG).show()
                }
            } )


            // To be replaced by retrofit code
            /* val staff = Staff()
             staff.id = id
             staff.name = name
             staff.contact = contact
             staff.address = address
 */
            //SampleData.updateDestination(staff);
            finish() // Move back to DestinationListActivity
        }
    }

    private fun initDeleteButton(id: Int) {

        btn_delete.setOnClickListener {

            val destinationService:DestinationService = ServiceBuilder.buildService(DestinationService::class.java)
            val requestCall:Call<Void> = destinationService.deleteStaff(id)
            requestCall.enqueue(object: Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                   if(response.isSuccessful){
                       Toast.makeText(this@DestinationDetailActivity,"Success", Toast.LENGTH_LONG).show()
                   }else{
                       Toast.makeText(this@DestinationDetailActivity,"Failure", Toast.LENGTH_LONG).show()
                   }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("TAG", t.toString())
                    Toast.makeText(this@DestinationDetailActivity,t.toString(), Toast.LENGTH_LONG).show()
                }
            } )

            // To be replaced by retrofit code
          //  SampleData.deleteDestination(id)
            finish() // Move back to DestinationListActivity
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, DestinationListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        const val ARG_ITEM_ID = "item_id"
    }
}
