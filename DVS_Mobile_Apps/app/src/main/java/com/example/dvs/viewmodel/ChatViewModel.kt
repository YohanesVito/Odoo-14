package com.example.dvs.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dvs.model.ContactModel
import com.example.dvs.remote.param.NotificationParam
import com.example.dvs.remote.response.NotificationResponse
import com.example.dvs.remote.retrofit.ApiConfig
import com.example.dvs.util.Result
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatViewModel(context: Context): ViewModel() {
    private val apiService = ApiConfig.getApiServiceChat()

    fun sendNotification(param: NotificationParam): LiveData<Result<Boolean>> {
        val result = MutableLiveData<Result<Boolean>>()
        result.value = Result.Loading
        apiService.sendNotification(param).enqueue(object : Callback<NotificationResponse> {
            override fun onResponse(
                call: Call<NotificationResponse>,
                response: Response<NotificationResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody?.success == 1){
                        Log.d("token",responseBody.success.toString())
                        result.value = Result.Success(true)
                    }
                }else {
                    result.value = Result.Error(response.message())
                    Log.d("fail1",response.errorBody().toString())
                    Log.d("fail1",response.toString())
                }
            }
            override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
                Log.d("fail2",t.message.toString())
                result.value = Result.Error("Can't Connect Retrofit")
            }
        })
        return result
    }

    fun getContacts(): MutableLiveData<ArrayList<ContactModel>>{

        val listUser = MutableLiveData<ArrayList<ContactModel>>()
        val db = Firebase.firestore
        val usersCollection = db.collection("DVS-User")

        usersCollection.get()
            .addOnSuccessListener { documents ->
                val users = ArrayList<ContactModel>()
                for (document in documents) {
                    Log.d("Firestore", "${document.id} => ${document.data}")
                    // Create a new UserModel object from the document data
                    val user = ContactModel(
                        uid = document.getString("uid")!!,
                        email = document.getString("email")!!,
                        avatar = document.getString("avatar")!!,
                        tokenFCM = document.getString("tokenFCM")!!
                    )
                    // Add the new user to the list of users
                    users.add(user)
                }
                // Update the MutableLiveData value with the list of users
                listUser.value = users
            }
            .addOnFailureListener { exception ->
                Log.w("Firestore", "Error getting documents: ", exception)
            }

        return listUser
    }

}