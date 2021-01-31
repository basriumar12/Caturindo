package com.caturindo.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TransportDto(

	@field:SerializedName("image")
	val image: List<String>? = null,

	@field:SerializedName("id_image")
	val idImage: String? = null,

	@field:SerializedName("name_transport")
	val nameTransport: String? = null,

	@field:SerializedName("status_booking")
	val statusBooking: Any? = null,

	@field:SerializedName("max_people")
	val maxPeople: String? = null,

	@field:SerializedName("id")
	val id: String? = null
) : Serializable
