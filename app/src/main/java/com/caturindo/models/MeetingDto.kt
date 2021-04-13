package com.caturindo.models

import com.google.gson.annotations.SerializedName

data class MeetingDto(

	@field:SerializedName("date")
	val date: String? = null,

//	@field:SerializedName("file")
//	val file: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("tag")
	val tag: String? = null,

	@field:SerializedName("title")
	val title: String? = null
//	,
//
//	@field:SerializedName("id_file")
//	val idFile: String? = null
)
