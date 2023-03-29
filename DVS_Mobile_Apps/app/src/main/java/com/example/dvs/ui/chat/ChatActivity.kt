package com.example.dvs.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.dvs.ViewModelFactory
import com.example.dvs.databinding.ActivityChatBinding
import com.example.dvs.remote.param.Notification
import com.example.dvs.remote.param.NotificationParam
import com.example.dvs.util.Result
import com.example.dvs.viewmodel.ChatViewModel

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var chatViewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupViewModel()
        setupAction()

    }

    private fun setupAction() {
       binding.btTestNotification.setOnClickListener{
           //dummy data
           val mNotification = Notification(
               title = "Halo Dari Emulator",
               body = "Selamat anda mendapatkan notifikasi",
               subtitle = "Firebase Cloud Message Subtitle"
           )
           val mNotificationParam = NotificationParam(
               //SM A32
               to = "f7et1j4GThWjrmFsxx28Xn:APA91bHuP_GRJn5PJJlHL4LiHxrAjzAGRf4DdiawMQxeoEovJ84nYUymwnH_UE-pMLZXXQO6eNBXe3nfaT5aOKEfjdLWZIPwxhlVWHgFLVlCjNoTzh9lCfufM86C8lHASTvlHU64yk7O",
               notification = mNotification,
           )
           chatViewModel.sendNotification(mNotificationParam).observe(this){
               when(it) {
                   is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                   is Result.Success -> {
                       binding.progressBar.visibility = View.GONE
                       Toast.makeText(this@ChatActivity, "Berhasil Mengirim Notifikasi", Toast.LENGTH_SHORT).show()
                   }
                   is Result.Error -> {
                       Toast.makeText(this@ChatActivity, "Gagal Mengirim Notifikasi", Toast.LENGTH_SHORT).show()
                       binding.progressBar.visibility = View.GONE
                   }

               }
           }
       }
    }

    private fun setupViewModel() {
        chatViewModel = ViewModelProvider(
            this,
            ViewModelFactory(this)
        )[ChatViewModel::class.java]
    }

}