package com.caturindo.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MeetingDtoNew(

        @field:SerializedName("date")
        val date: String? = null,

        @field:SerializedName("data_s_meeting")
        val dataSubMeeting: List<DataSubMeetingItemItem>? = null,

        @field:SerializedName("sub_meting")
        val subMeting: String? = null,

        @field:SerializedName("description")
        val description: String? = null,

        @field:SerializedName("id_user")
        val idUser: String? = null,

        @field:SerializedName("title")
        val title: String? = null,

        @field:SerializedName("id_booking")
        val idBooking: String? = null,

        @field:SerializedName("file")
        val file: List<String>? = null,

        @field:SerializedName("location")
        val location: String? = null,
        @field:SerializedName("nama_group")
        val nama_group: String? = null,

        @field:SerializedName("id")
        val id: String? = null,

        @field:SerializedName("time")
        val time: String? = null,

        @field:SerializedName("tag")
        val tag: String? = null,

        @field:SerializedName("status_meeting")
        val statusMeeting: String? = null,

        @field:SerializedName("count_members")
        val countMembers: Int? = 0,

        @field:SerializedName("id_file")
        val idFile: List<String>? = null
) : Serializable


data class DataSubMeetingItemItem(

        @field:SerializedName("date")
        val date: String? = null,

        @field:SerializedName("time_start")
        val timeStart: String? = null,

        @field:SerializedName("id_meeting")
        val idMeeting: String? = null,

        @field:SerializedName("description")
        val description: String? = null,
        @field:SerializedName("nama_group")
        val nama_group: String? = null,

        @field:SerializedName("created_at")
        val createdAt: String? = null,

        @field:SerializedName("id_task")
        val idTask: String? = null,

        @field:SerializedName("id_user")
        val idUser: String? = null,

        @field:SerializedName("title")
        val title: String? = null,

        @field:SerializedName("id_booking")
        val idBooking: String? = null,

        @field:SerializedName("updated_at")
        val updatedAt: String? = null,

        @field:SerializedName("location")
        val location: String? = null,

        @field:SerializedName("id")
        val id: String? = null,

        @field:SerializedName("time")
        val time: String? = null,

        @field:SerializedName("tag")
        val tag: String? = null,

        @field:SerializedName("time_end")
        val timeEnd: String? = null,

        @field:SerializedName("status_meeting")
        val statusMeeting: String? = null,

        @field:SerializedName("id_file")
        val idFile: List<String>? = null,
        @field:SerializedName("file")
        val file: List<String>? = null,
        @field:SerializedName("count_members")
        val countMembers: Int? = 0
) : Serializable
