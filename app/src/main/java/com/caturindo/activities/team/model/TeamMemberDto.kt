package com.caturindo.activities.team.model

import com.google.gson.annotations.SerializedName

data class TeamMemberDto(

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("jabatan")
	val jabatan: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("member")
	val member: List<MemberItem?>? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)

data class MemberItem(

	@field:SerializedName("whatsapp")
	val whatsapp: Any? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("jabatan")
	val jabatan: String? = null,

	@field:SerializedName("id_member")
	val idMember: String? = null,

	@field:SerializedName("username")
	val username: String? = null,
	@field:SerializedName("email")
	val email: String? = null
)
