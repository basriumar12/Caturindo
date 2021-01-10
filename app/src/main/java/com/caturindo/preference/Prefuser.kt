package com.caturindo.preference

import com.caturindo.constant.Constant
import com.caturindo.models.RegisterDto
import com.orhanobut.hawk.Hawk

class Prefuser{
    fun setUser(user: RegisterDto?) = Hawk.put(Constant.USER_PREF, user)

    fun getUser(): RegisterDto? = Hawk.get(Constant.USER_PREF, null)

}