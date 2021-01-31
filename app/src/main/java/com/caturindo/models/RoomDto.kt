package com.caturindo.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RoomDto(

	@field:SerializedName("image")
	val image: List<String?>? = null,

	@field:SerializedName("name_ruangan")
	val nameRoom: String? = null,

	@field:SerializedName("status_booking")
	val statusBooking: String? = null,

	@field:SerializedName("max_people")
	val maxPeople: String? = null,

	@field:SerializedName("code_room")
	val codeRoom: String? = null
) : Serializable


