package com.example.dvs.ui.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dvs.ViewModelFactory
import com.example.dvs.databinding.ActivityListContactBinding
import com.example.dvs.model.ContactModel
import com.example.dvs.util.Constant.Companion.NAME
import com.example.dvs.viewmodel.ChatViewModel


class ListContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListContactBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupViewModel()
        setupAction()

    }

    private fun setupAction() {
        val contacts = listOf(
            ContactModel("https://static.dw.com/image/63246417_604.jpg", "Edward Snowden"),
            ContactModel("https://s.kaskus.id/images/2021/09/01/8937321_20210901110034.jpg", "Sandhika Galih"),
            ContactModel("http://www.sinvestir.org/wp-content/uploads/2020/01/005710200_1500881661-1.jpg", "Gill Bates")
        )
        val recyclerView = binding.rvReceiver
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ContactAdapter(contacts)
        recyclerView.adapter = adapter

        adapter.setOnItemClickCallback(object : ContactAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ContactModel) {
                startChatActivity(data)
            }
        })
    }
    private fun startChatActivity(mContact: ContactModel) {
        val intentToChat = Intent(this,ChatActivity::class.java)
        intentToChat.putExtra(NAME,mContact.name)
        startActivity(intentToChat)
    }
    private fun setupViewModel() {

    }

}