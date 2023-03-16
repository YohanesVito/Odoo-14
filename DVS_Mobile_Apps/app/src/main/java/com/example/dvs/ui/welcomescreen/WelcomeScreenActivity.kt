package com.example.dvs.ui.welcomescreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.dvs.R
import com.example.dvs.ui.login.LoginActivity

class WelcomeScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)
        supportActionBar?.hide()

        val btContinue = findViewById<Button>(R.id.bt_continue)

        btContinue.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}