package com.caturindo.models

import com.alibaba.fastjson.annotation.JSONField

data class TransportDto(

	@JSONField(name="image")
	val image: List<String?>? = null,

	@JSONField(name="id_image")
	val idImage: String? = null,

	@JSONField(name="name_transport")
	val nameTransport: String? = null,

	@JSONField(name="status_booking")
	val statusBooking: Any? = null,

	@JSONField(name="max_people")
	val maxPeople: String? = null,

	@JSONField(name="id")
	val id: String? = null
)
