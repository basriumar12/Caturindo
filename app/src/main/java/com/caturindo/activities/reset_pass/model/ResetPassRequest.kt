package com.caturindo.activities.reset_pass.model

import com.google.gson.annotations.SerializedName

data class ResetPassRequest(

	@field:SerializedName("email")
	val email: String? = null
)
