package com.caturindo.activities.team.model

import com.google.gson.annotations.SerializedName

data class EditUserRequest(

	@field:SerializedName("whatsapp")
	val whatsapp: String? = null,

	@field:SerializedName("no_hp")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
