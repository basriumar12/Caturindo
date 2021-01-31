package com.caturindo.activities.team.edit

import com.caturindo.activities.team.model.EditUserRequest
import com.caturindo.activities.team.model.UpdateUploadUserDto
import com.caturindo.models.RegisterDto
import com.caturindo.models.UserDto
import com.caturindo.models.UserDtoNew
import okhttp3.MultipartBody
import okhttp3.RequestBody


interface EditUserContract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun onSuccessGet(data : UpdateUploadUserDto)
        fun onSuccessGetBackground(data : UpdateUploadUserDto)
        fun onErrorGetData(msg: String?)
        fun onSuccessEditUser(msg: String?)
        fun onSuccessGetDetail(data : UserDtoNew)
    }

    interface Presenter {

        fun getUser(idUser : String)
        fun editUser(data : EditUserRequest)
        fun uploadImage(idUser : RequestBody, file: MultipartBody.Part)
        fun uploadBackgroundImage(idUser : RequestBody, file: MultipartBody.Part)

    }
}