package com.example.dvs.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dvs.model.NotificationModel

class NotificationViewModel(context: Context)  : ViewModel() {

    private val notificationLiveData = MutableLiveData<NotificationModel>()

    fun getNotificationLiveData(): LiveData<NotificationModel> = notificationLiveData

    // This function will be called when a new FCM notification is received
    fun generateNotification(title: String?, message: String?) {
        val notificationData = NotificationModel(title, message)
        notificationLiveData.postValue(notificationData)
    }

}