package com.caturindo.models

import com.google.gson.annotations.SerializedName

data class MeetingRequest(

	@field:SerializedName("id_booking")
	var idBooking: String? = null,

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("tag")
	val tag: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("id_file")
	val idFile: Int? = null
)
