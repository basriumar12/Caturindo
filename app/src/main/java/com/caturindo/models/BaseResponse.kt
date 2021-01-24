package com.caturindo.models

 data class BaseResponse<T> (
     var message: String? = null,
     var status: Boolean?  = null,
     val data: T
 )