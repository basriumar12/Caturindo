package com.caturindo

import android.app.Application
import android.util.Log
import com.orhanobut.hawk.Hawk

open class AppControler : Application(){

    override fun onCreate() {
        super.onCreate()
        Hawk.init(applicationContext).setLogInterceptor { message -> if (BuildConfig.DEBUG) Log.d("Hawk", message) }
                .build()

    }

}