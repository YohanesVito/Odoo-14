package com.example.dvs.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.dvs.ViewModelFactory
import com.example.dvs.databinding.ActivityChat2Binding
import com.example.dvs.model.ContactModel
import com.example.dvs.util.Constant
import com.example.dvs.viewmodel.ChatViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.UUID

class DetailChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChat2Binding
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var mReceiver: ContactModel
    private lateinit var currentUser: FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChat2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupReceiver()
        setupFirebaseUser()
        setupViewModel()
        setupAction()

    }
    private fun setupReceiver() {
        // Retrieve the bundle from the intent
        val bundle = intent.extras

        // Extract the ContactModel object from the bundle
        mReceiver = bundle?.getParcelable<ContactModel>(Constant.CONTACT)!!

        //setup photo Profile
        Glide.with(this)
            .load(mReceiver.avatar)
            .circleCrop()
            .into(binding.ivProfile)

        //setup profile name
        binding.tvName.text = mReceiver.email
    }

    private fun setupFirebaseUser() {
        auth = Firebase.auth
        currentUser = auth.currentUser!!
    }

    private fun setupAction() {
        //setup RecyclerView
        val linearLayoutManager = LinearLayoutManager(this)
        binding.rvChat.layoutManager = linearLayoutManager

        getAllChat()
        val mContact = intent.getParcelableExtra<ContactModel>(Constant.CONTACT)

        binding.ivSend.setOnClickListener {
            val message = binding.etMessageBox.text.toString()
            if (message != "") {
                Log.d("pesan",message)
                chatViewModel.sendMessage(
                    chatId = UUID.randomUUID().toString(),
                    senderId = currentUser.uid,
                    recipientId = mContact?.uid.toString(),
                    content = message,
                )
                binding.etMessageBox.setText("")
            }
        }

//       binding.btTestNotification.setOnClickListener{
//
//           //dummy data
//           val mNotification = Notification(
//               title = "Halo Dari ${currentUser.displayName}",
//               body = "Selamat anda mendapatkan notifikasi",
//               subtitle = "Firebase Cloud Message Subtitle"
//           )
//           val mNotificationParam = NotificationParam(
//               //SM A32
//               to = mContact?.tokenFCM,
//               notification = mNotification,
//           )
//           chatViewModel.sendNotification(mNotificationParam).observe(this){
//               when(it) {
//                   is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
//                   is Result.Success -> {
//                       binding.progressBar.visibility = View.GONE
//                       Toast.makeText(this@DetailChatActivity, "Berhasil Mengirim Notifikasi", Toast.LENGTH_SHORT).show()
//                   }
//                   is Result.Error -> {
//                       Toast.makeText(this@DetailChatActivity, "Gagal Mengirim Notifikasi", Toast.LENGTH_SHORT).show()
//                       binding.progressBar.visibility = View.GONE
//                   }
//
//               }
//           }
//       }

    }

    private fun getAllChat() {
        chatViewModel.getAllChat().observe(this){
            val adapter = ChatAdapter(it)
            Log.d("test",it.toString())
            binding.rvChat.adapter = adapter
            binding.rvChat.scrollToPosition(adapter.itemCount - 1)
        }
    }

    private fun setupViewModel() {
        chatViewModel = ViewModelProvider(
            this,
            ViewModelFactory(this)
        )[ChatViewModel::class.java]

    }

}