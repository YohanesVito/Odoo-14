package com.example.dvs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dvs.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

   // private lateinit var monitorViewModel: MonitorViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btLogin.setOnClickListener {
            startActivity(Intent(this,ListProductActivity::class.java))
        }

    }
}