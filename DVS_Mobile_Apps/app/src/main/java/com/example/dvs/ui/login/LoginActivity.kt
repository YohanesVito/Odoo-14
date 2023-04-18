package com.example.dvs.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dvs.R
import com.example.dvs.ViewModelFactory
import com.example.dvs.databinding.ActivityLoginBinding
import com.example.dvs.ui.home.HomeActivity
import com.example.dvs.util.TokenGenerator
import com.example.dvs.util.Result
import com.example.dvs.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleAccount: GoogleSignInAccount


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupViewModel()
        setupAction()

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this,gso)
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null) {
            reload()
        }
    }

    private fun reload() {
        Toast.makeText(this,"No User",Toast.LENGTH_SHORT).show()
    }


    private fun setupAction() {

        binding.btLogin.setOnClickListener {
            login()
        }

        binding.btLogout.setOnClickListener {
            Firebase.auth.signOut()
            googleSignInClient.signOut()
            Toast.makeText(this,"Berhasil Logout",Toast.LENGTH_SHORT).show()
        }

        binding.btLoginGoogle.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            signInGoogle()
        }

    }

    private fun saveUserToFirestore() {
        val uid = Firebase.auth.currentUser?.uid
        val email = Firebase.auth.currentUser?.email!!
        val avatar = Firebase.auth.currentUser?.photoUrl.toString()

        TokenGenerator().getFCMToken { token ->
            Log.d("Token Generator: ", token)
            // Do something with the token value here
            if (uid != null) {
                loginViewModel.saveUsertoRealtimeDatabase(uid,email,avatar,token)
            }
        }
    }


    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
    }

    private val signInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { res ->
        if (res.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(res.data)
            handleResult(task)
        }
    }

    private fun handleResult(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if (account != null){
                updateUI(account)
            }
        }else{
            Toast.makeText(this,task.exception.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken,null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if(it.isSuccessful){
//                Toast.makeText(this,account.displayName,Toast.LENGTH_SHORT).show()
//                Log.i("AKUN",account.toString())
                Log.i("AKUN",account.id.toString())
                Log.i("AKUN",account.idToken.toString())
                googleAccount = account
//                Log.i("AKUN",account.displayName.toString())
//                Log.i("AKUN",account.email.toString())
//                Log.i("AKUN",account.photoUrl.toString())

                binding.progressBar.visibility = View.GONE
                loginWithGoogle(googleAccount)
                saveUserToFirestore()

            }else{
                Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
            }
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

                    //panggil fungsi untuk menyimpan data di FireStore
                    Toast.makeText(this@LoginActivity, "Berhasil Masuk", Toast.LENGTH_SHORT).show()
                    val intentToHome = Intent(this, HomeActivity::class.java)
                    startActivity(intentToHome)
                    finish()
                }
                is Result.Error -> {
                    Toast.makeText(this@LoginActivity, "Gagal Masuk, User Tidak Terdaftar", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                }

            }
        }

    }

    private fun loginWithGoogle(account: GoogleSignInAccount) {
        val username = account.displayName.toString()
        val provider = "Google"
        val access = account.idToken.toString()
        val oauthUid = account.id.toString()
        loginViewModel.loginWithGoogle(username = username,access = access, provider = provider, oauth_uid = oauthUid).observe(this) {
            when(it) {
                is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this@LoginActivity, "Berhasil Masuk", Toast.LENGTH_SHORT).show()
                    val intentToHome = Intent(this, HomeActivity::class.java)
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