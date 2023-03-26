package com.example.dvs.ui.notification


import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val notificationViewModel: NotificationViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(NotificationViewModel::class.java)
    }



    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Get notification data from the RemoteMessage object
        val title = remoteMessage.notification?.title
        val message = remoteMessage.notification?.body

        // Call the handleNotification function in the ViewModel
        notificationViewModel.handleNotification(title, message)
    }

    override fun onNewToken(token: String) {
        Log.d("TokenMessagingServices", "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
//        sendRegistrationToServer(token)
    }
}
