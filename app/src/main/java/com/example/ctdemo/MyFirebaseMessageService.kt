package com.example.ctdemo

import android.os.Bundle
import android.util.Log
import com.clevertap.android.sdk.CleverTapAPI
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessageService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        try {
            if (message.data.isNotEmpty()) {
                val extras = Bundle()
                for ((key, value) in message.data) {
                    extras.putString(key, value)
                }
                Log.e("TAG", "onReceived Mesaage Called")
                val info = CleverTapAPI.getNotificationInfo(extras)
                if (info.fromCleverTap) {
                    CleverTapAPI.createNotification(applicationContext, extras)
                }
            }
        } catch (t: Throwable) {
            Log.d("MYFCMLIST", "Error parsing FCM message", t)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        MyApplication.getCleverTapDefaultInstance().pushFcmRegistrationId(token, true)
    }
}