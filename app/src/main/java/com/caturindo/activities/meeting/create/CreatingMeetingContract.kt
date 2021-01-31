package com.caturindo.activities.meeting.create

import com.caturindo.activities.meeting.create.model.UploadDto
import com.caturindo.models.CancelMeetingRequest
import com.caturindo.models.MeetingRequest
import com.caturindo.models.RegisterDto
import okhttp3.MultipartBody
import java.io.File


interface CreatingMeetingContract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun successUpload(msg : String, uploadDto: UploadDto)
        fun failUpload (msg : String)
        fun failCreate (msg : String)
        fun succesCreate(msg : String)

    }

    interface Presenter {
        fun uploadFile(file : MultipartBody.Part)
        fun postCreate(meetingRequest: MeetingRequest, idParentMeeting : String)
    }
}