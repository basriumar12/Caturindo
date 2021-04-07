package com.caturindo.activities.grup

import com.caturindo.activities.meeting.create.model.UploadDto
import com.caturindo.models.CancelMeetingRequest
import com.caturindo.models.MeetingRequest
import com.caturindo.models.RegisterDto
import okhttp3.MultipartBody
import java.io.File


interface CreatingGrupContract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun succesCreate(msg : String)
        fun failCreate(msg : String)

    }

    interface Presenter {

        fun postCreate( namaGrup : String)

        fun getGrup()
    }
}