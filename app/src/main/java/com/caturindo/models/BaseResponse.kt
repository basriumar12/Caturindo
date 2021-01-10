package com.caturindo.models

 data class BaseResponse<T> (
     var message: String? = null,
     var status: String? = null,
     val data: T
 )