package com.example.dvs.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.dvs.ui.listproduct.ListProductActivity
import com.example.dvs.ViewModelFactory
import com.example.dvs.databinding.ActivityLoginBinding
import com.example.dvs.viewmodel.LoginViewModel
import com.example.dvs.util.Result

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupViewModel()
        binding.btLogin.setOnClickListener {
            login()
        }

    }

    private fun login() {
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()

        loginViewModel.login(username, password).observe(this) {
            when(it) {
                is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this@LoginActivity, "Berhasil Masuk", Toast.LENGTH_SHORT).show()
                    val intentToHome = Intent(this, ListProductActivity::class.java)
                    startActivity(intentToHome)
                    finish()
                }
                is Result.Error -> {
                    Toast.makeText(this@LoginActivity, "Gagal Masuk", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                }

            }
        }

    }

    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(this)
        )[LoginViewModel::class.java]

    }
}