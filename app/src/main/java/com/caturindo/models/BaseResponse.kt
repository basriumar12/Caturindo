package com.caturindo.models

 data class BaseResponse<T> (
     var message: String? = null,
     var status: Boolean?  = null,
     val data: T? = null
 )

data class BaseResponseOther (
     var message: String? = null,
     var status: Boolean?  = null

 )