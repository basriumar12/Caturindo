package com.caturindo.fragments.task

import com.caturindo.models.RegisterDto
import com.caturindo.models.TaskDto


interface TaskContract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun onSuccessGet(data : MutableList<TaskDto>)
        fun onErrorGetData(msg: String?)
    }

    interface Presenter {
        fun getTask()

    }
}