package com.caturindo.models

import com.google.gson.annotations.SerializedName

data class UpdateTokenRequest(

	@field:SerializedName("token_firebase")
	val tokenFirebase: String? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null
)
