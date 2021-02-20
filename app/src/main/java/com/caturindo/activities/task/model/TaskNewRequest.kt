package com.caturindo.activities.task.model

import com.google.gson.annotations.SerializedName

data class TaskNewRequest(

	@field:SerializedName("id_task")
	val idTask: String? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null
)
