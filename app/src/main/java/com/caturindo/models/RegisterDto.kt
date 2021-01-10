package com.caturindo.models

import com.google.gson.annotations.SerializedName

data class RegisterDto(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("jabatan")
	val jabatan: String? = null,

	@field:SerializedName("role")
	val role: String? = null,
	@field:SerializedName("message")
	val message: String? = null
)
