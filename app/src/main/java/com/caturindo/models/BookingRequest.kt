package com.caturindo.models

import com.google.gson.annotations.SerializedName

data class BookingRequest(

	@field:SerializedName("code_transport")
	val codeTransport: String? = null,

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("driver_name")
	val driverName: String? = null,

	@field:SerializedName("note")
	val note: String? = null,

	@field:SerializedName("time_start")
	val timeStart: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null,

	@field:SerializedName("time_end")
	val timeEnd: String? = null,

	@field:SerializedName("code_room")
	val codeRoom: String? = null
)
