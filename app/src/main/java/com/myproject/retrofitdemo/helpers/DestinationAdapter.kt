package com.smartherd.globofly.helpers

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.smartherd.globofly.activities.DestinationDetailActivity
import com.smartherd.globofly.models.Destination
import com.myproject.retrofitdemo.R
import com.myproject.retrofitdemo.models.Staff

class DestinationAdapter(private val staffList: List<Staff>) : RecyclerView.Adapter<DestinationAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {

		holder.staff = staffList[position]
		holder.txvStaffName.text = staffList[position].name

		holder.itemView.setOnClickListener { v ->
			val context = v.context
			val intent = Intent(context, DestinationDetailActivity::class.java)
			intent.putExtra(DestinationDetailActivity.ARG_ITEM_ID, holder.staff!!.id)

			context.startActivity(intent)
		}
	}

	override fun getItemCount(): Int {
		return staffList.size
	}

	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

		val txvStaffName: TextView = itemView.findViewById(R.id.txv_destination)
		var staff: Staff? = null

		override fun toString(): String {
			return """${super.toString()} '${txvStaffName.text}'"""
		}
	}
}
