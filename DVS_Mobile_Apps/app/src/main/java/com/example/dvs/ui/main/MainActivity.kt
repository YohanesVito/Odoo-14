package com.example.dvs.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dvs.databinding.ActivityMainBinding
import com.example.dvs.ui.chat.ListContactActivity
import com.example.dvs.ui.listproduct.ListProductActivity
import com.example.dvs.ui.login.LoginActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupAction()
    }

    private fun setupAction() {
        val user = Firebase.auth.currentUser
        if (user == null) {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        binding.ivChat.setOnClickListener {
            startActivity(Intent(this,ListContactActivity::class.java))
        }

        binding.ivProduct.setOnClickListener {
            startActivity(Intent(this,ListProductActivity::class.java))
        }

    }
}