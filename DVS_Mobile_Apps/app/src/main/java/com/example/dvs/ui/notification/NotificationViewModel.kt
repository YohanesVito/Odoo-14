package com.example.dvs.ui.notification

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dvs.model.NotificationModel
import com.google.firebase.messaging.RemoteMessage

class NotificationViewModel(context: Context)  : ViewModel() {

    private val notificationLiveData = MutableLiveData<NotificationModel>()

    fun getNotificationLiveData(): LiveData<NotificationModel> = notificationLiveData

    // This function will be called when a new FCM notification is received
    fun handleNotification(title: String?, message: String?) {
        val notificationData = NotificationModel(title, message)
        notificationLiveData.postValue(notificationData)
    }

    //=============================================
    private val notificationData = MutableLiveData<RemoteMessage>()

    fun getNotificationData(): LiveData<RemoteMessage> {
        return notificationData
    }

    fun setNotificationData(remoteMessage: RemoteMessage) {
        notificationData.value = remoteMessage
    }

}