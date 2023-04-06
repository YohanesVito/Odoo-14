package com.example.dvs.ui.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dvs.ViewModelFactory
import com.example.dvs.databinding.ActivityListContactBinding
import com.example.dvs.model.NewContactModel
import com.example.dvs.util.Constant.Companion.CONTACT
import com.example.dvs.util.Constant.Companion.NAME
import com.example.dvs.viewmodel.ListContactViewModel



class ListContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListContactBinding
    private lateinit var listContactViewModel: ListContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListContactBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupViewModel()
        setupAction()

    }

    private fun setupAction() {
        val recyclerView = binding.rvReceiver
        recyclerView.layoutManager = LinearLayoutManager(this)
        listContactViewModel.getContacts().observe(this){
            Log.d("AllUser",it.toString())
            val adapter = ContactAdapter(it)
            recyclerView.adapter = adapter
            adapter.setOnItemClickCallback(object : ContactAdapter.OnItemClickCallback {
                override fun onItemClicked(data: NewContactModel) {
                    startChatActivity(data)
                }
            })
        }

    }
    private fun startChatActivity(mContact: NewContactModel) {
        //kirim bundle
        val bundle = Bundle().apply {
            putParcelable(CONTACT, mContact)
        }
        val intent = Intent(this, ChatActivity::class.java).apply {
            putExtras(bundle)
        }
        startActivity(intent)
    }
    private fun setupViewModel() {
        listContactViewModel = ViewModelProvider(
            this,
            ViewModelFactory(this)
        )[ListContactViewModel::class.java]

    }

}