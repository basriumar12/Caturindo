package com.caturindo.activities.grup.model

import com.google.gson.annotations.SerializedName

data class ResponseGroupDto(

	@field:SerializedName("nama_team")
	val namaTeam: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null,

	@field:SerializedName("total_anggota")
	val total_anggota: String? = null
)
