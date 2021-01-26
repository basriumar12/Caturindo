package com.caturindo.activities.meeting.create.model

import com.google.gson.annotations.SerializedName

data class UploadDto(

	@field:SerializedName("file")
	val file: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
