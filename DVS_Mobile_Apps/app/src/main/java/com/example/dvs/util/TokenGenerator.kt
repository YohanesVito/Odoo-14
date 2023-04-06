package com.example.dvs.util

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class TokenGenerator {
    fun generateFCMToken(){
        var token = ""
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("Notification Activity", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            token = task.result
            Log.d("FCM registration token", token)
        })
        Log.d("Token Generator: ", token)
    }

    fun getFCMToken(callback: (String) -> Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("Notification Activity", "Fetching FCM registration token failed", task.exception)
                callback("")
                return@addOnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Log.d("FCM registration token", token)

            callback(token ?: "")
        }
    }

}