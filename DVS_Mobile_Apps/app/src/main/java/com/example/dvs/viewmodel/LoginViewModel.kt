package com.example.dvs.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dvs.model.ContactModel
import com.example.dvs.remote.retrofit.ApiConfig
import com.example.dvs.remote.response.LoginResponse
import com.example.dvs.model.UserPreference
import com.example.dvs.remote.param.AuthParam
import com.example.dvs.ui.splashscreen.dataStore
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(context: Context): ViewModel(){
    private val apiService = ApiConfig.getApiService()
    val userPreference = UserPreference.getInstance(context.dataStore)


    fun login(username: String, password: String): LiveData<com.example.dvs.util.Result<Boolean>> {
        val result = MutableLiveData<com.example.dvs.util.Result<Boolean>>()
        result.value = com.example.dvs.util.Result.Loading
        apiService.getToken(username, password).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null){
                        result.value = com.example.dvs.util.Result.Success(true)

                        Log.d("token",responseBody.accessToken!!)

                        MainScope().launch {
                            userPreference.saveUser(
                                username = username,
                                token = responseBody.accessToken,
                            )
                            userPreference.login()
                        }
                        result.value = com.example.dvs.util.Result.Success(true)
                    }
                }else {
                    result.value = com.example.dvs.util.Result.Error(response.message())
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                result.value = com.example.dvs.util.Result.Error("Can't Connect Retrofit")
            }
        })
        return result
    }

    fun loginWithGoogle(username: String,access: String, provider: String, oauth_uid: String): LiveData<com.example.dvs.util.Result<Boolean>> {
        val result = MutableLiveData<com.example.dvs.util.Result<Boolean>>()
        result.value = com.example.dvs.util.Result.Loading
        val mAuthParam = AuthParam(accessToken = access, provider = provider, oauth_uid = oauth_uid)
        apiService.signInToOdoo(mAuthParam).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null){
                        result.value = com.example.dvs.util.Result.Success(true)

                        Log.i("TOKEN TEST LOGIN",responseBody.accessToken!!)
                        MainScope().launch {
                            userPreference.saveUser(
                                username = username,
                                token = responseBody.accessToken,
                            )
                            userPreference.login()
                        }
                        result.value = com.example.dvs.util.Result.Success(true)
                    }
                }else {
                    Log.i("Not Success",response.toString())
                    result.value = com.example.dvs.util.Result.Error(response.message())
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                result.value = com.example.dvs.util.Result.Error("Can't Connect Retrofit")

            }
        })
        return result
    }

    fun saveUsertoFireStore(uid: String, email: String, avatar: String, tokenFCM: String){
        val db = Firebase.firestore
        val user = ContactModel(uid,email,avatar,tokenFCM)

        db.collection("DVS-User")
            .whereEqualTo("uid",user.uid)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    // No matching documents, so add new user
                    db.collection("DVS-User")
                        .add(user)
                        .addOnSuccessListener { documentReference ->
                            Log.d("LoginViewModel", "DocumentSnapshot added with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            Log.w("LoginViewModel", "Error adding document", e)
                        }
                } else {
                    // User already exists, so do nothing or show an error message
                    Log.d("LoginViewModel", "User with email already exists")
                }
            }
            .addOnFailureListener { e ->
                Log.w("LoginViewModel", "Error checking for user with email", e)
            }
    }

    fun saveUsertoRealtimeDatabase(uid: String, email: String, avatar: String, tokenFCM: String) {
        val db = Firebase.database
        val user = db.reference.child("users")

        user.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()) {
                    // User does not exist, so add new user
                    val userData = mapOf(
                        "email" to email,
                        "avatar" to avatar,
                        "token" to tokenFCM
                    )
                    user.child(uid).setValue(userData)
                        .addOnSuccessListener {
                            Log.d("LoginViewModel", "User added to Realtime Database")
                        }
                        .addOnFailureListener { e ->
                            Log.w("LoginViewModel", "Error adding user to Realtime Database", e)
                        }
                } else {
                    // User already exists, so do nothing or show an error message
                    Log.d("LoginViewModel", "User already exists in Realtime Database")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("LoginViewModel", "Error checking for user in Realtime Database", error.toException())
            }
        })
    }

}