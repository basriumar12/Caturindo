package com.caturindo.activities.team.model

import com.google.gson.annotations.SerializedName

data class UpdateUploadUserDto(

	@field:SerializedName("whatsapp")
	val whatsapp: String? = null,

	@field:SerializedName("user_aktif")
	val userAktif: String? = null,

	@field:SerializedName("id_image_background")
	val idImageBackground: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("id_image_profile")
	val idImageProfile: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("phone")
	val phone: Any? = null,

	@field:SerializedName("image_profile")
	val imageProfile: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("image_background")
	val imageBackground: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
