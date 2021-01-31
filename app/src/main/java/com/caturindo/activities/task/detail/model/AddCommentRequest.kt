package com.caturindo.activities.task.detail.model

import com.google.gson.annotations.SerializedName

data class AddCommentRequest(

	@field:SerializedName("id_task")
	val idTask: String? = null,

	@field:SerializedName("comment")
	val comment: String? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null
)
