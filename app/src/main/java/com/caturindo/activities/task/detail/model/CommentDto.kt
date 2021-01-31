package com.caturindo.activities.task.detail.model

import com.google.gson.annotations.SerializedName

data class CommentDto(

	@field:SerializedName("comment")
	val comment: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
