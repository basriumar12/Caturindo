package com.caturindo.service

import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject

/**
 * Created by Dimas Aprizawandi on 04/03/2020
 * Email : animatorist@gmail.com
 * Mobile App Developer
 */
@RequiresApi(api = Build.VERSION_CODES.M)
class FirebaseMessageService : FirebaseMessagingService() {
    private val mNotifyManager: NotificationManager? = null
    override fun onNewToken(s: String) {
        super.onNewToken(s)

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val title = remoteMessage.notification?.title
        val body = remoteMessage.notification?.body
       // val image = remoteMessage.notification?.imageUrl
        Log.e("TAG","body fcm ${remoteMessage.data}")
        Log.e("TAG","body fcm ${remoteMessage.notification}")



        if (remoteMessage.data.isNotEmpty()) { //

            val jsonObject = JSONObject(remoteMessage.data as Map<*, *>)
            if (jsonObject != null) {
                val jsonObjectTitle = jsonObject.getString("title")
                val jsonObjectBody = jsonObject.getString("body")
                //val id = jsonObject.getString("id").toString()
                NotificationManagers(this).displayNotification(jsonObjectTitle, jsonObjectBody, "image", "id")

            }
            }


        }


}