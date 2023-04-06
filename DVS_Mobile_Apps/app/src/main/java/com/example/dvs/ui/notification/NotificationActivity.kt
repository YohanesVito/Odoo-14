package com.example.dvs.ui.notification


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import com.example.dvs.R
import com.example.dvs.ViewModelFactory
import com.example.dvs.databinding.ActivityNotificationBinding
import com.example.dvs.util.TokenGenerator
import com.example.dvs.viewmodel.NotificationViewModel

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    private lateinit var notificationViewModel: NotificationViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

        TokenGenerator().generateFCMToken()

        // Observe the notificationLiveData object
        notificationViewModel.getNotificationLiveData().observe(this) {
            showNotification(it.title, it.message)
        }

        binding.btShowNotification.setOnClickListener {
            //send notification to another user

        }


    }

    private fun setupViewModel() {
        notificationViewModel = ViewModelProvider(
            this,
            ViewModelFactory(this)
        )[NotificationViewModel::class.java]

    }

    private fun showNotification(title: String?, message: String?) {
        // Create notification using NotificationCompat library
        val notificationBuilder = NotificationCompat.Builder(this, "channel_id")
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Show the notification
        with(NotificationManagerCompat.from(this)) {
            notify(0, notificationBuilder.build())
        }
    }
}
