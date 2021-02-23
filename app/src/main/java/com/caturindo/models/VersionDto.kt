package com.caturindo.models

import com.google.gson.annotations.SerializedName

data class VersionDto(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("version")
	val version: String? = null
)
