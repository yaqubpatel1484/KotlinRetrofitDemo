package com.smartherd.globofly.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.myproject.retrofitdemo.R
import com.myproject.retrofitdemo.models.Staff
import com.myproject.retrofitdemo.services.DestinationService
import com.myproject.retrofitdemo.services.ServiceBuilder
import com.smartherd.globofly.helpers.SampleData
import com.smartherd.globofly.models.Destination
import kotlinx.android.synthetic.main.activity_destiny_create.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DestinationCreateActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_destiny_create)

		setSupportActionBar(toolbar)
		val context = this

		// Show the Up button in the action bar.
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		btn_add.setOnClickListener {


			val newStaff = Staff()
			newStaff.name = et_name.text.toString()
			newStaff.contact = et_contact.text.toString()
			newStaff.address = et_address.text.toString()

			// To be replaced by retrofit code
//			SampleData.addDestination(newDestination)

			val destinationService:DestinationService = ServiceBuilder.buildService(DestinationService::class.java)
			val request = destinationService.addStaff(et_name.text.toString(),et_contact.text.toString(), et_address.text.toString())
			request.enqueue(object: Callback<String>{
				override fun onResponse(call: Call<String>, response: Response<String>) {

					if(response.isSuccessful){
						Toast.makeText(this@DestinationCreateActivity,"Success- ${response.body()}", Toast.LENGTH_LONG).show()
					}else{
						Toast.makeText(this@DestinationCreateActivity,"Failure", Toast.LENGTH_LONG).show()
					}

				}

				override fun onFailure(call: Call<String>, t: Throwable) {
					Log.e("TAG", t.toString())
					Toast.makeText(this@DestinationCreateActivity,t.toString(), Toast.LENGTH_LONG).show()
				}
			})

            finish() // Move back to DestinationListActivity
		}
	}
}
