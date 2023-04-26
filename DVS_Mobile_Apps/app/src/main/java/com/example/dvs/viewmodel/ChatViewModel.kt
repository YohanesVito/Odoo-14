package com.example.dvs.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dvs.model.ChatModel
import com.example.dvs.model.ContactModel
import com.example.dvs.model.MessageModel
import com.example.dvs.remote.param.NotificationParam
import com.example.dvs.remote.response.NotificationResponse
import com.example.dvs.remote.retrofit.ApiConfig
import com.example.dvs.util.Result
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
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

    fun sendMessage(chatId: String, senderId: String, recipientId: String, content: String) {
        val database = Firebase.database
        val messagesRef = database.getReference("chats/$chatId/messages")
        val messageId = messagesRef.push().key
        val timestamp = System.currentTimeMillis()

        if (messageId != null) {
            val message = HashMap<String, Any>()
            message["messageId"] = messageId
            message["senderId"] = senderId
            message["recipientId"] = recipientId
            message["timestamp"] = timestamp
            message["content"] = content
            messagesRef.child(messageId).setValue(message)
        }
    }

    fun getAllChat(): LiveData<List<ChatModel>> {
        val chatList = MutableLiveData<List<ChatModel>>()

        val chatRef = FirebaseDatabase.getInstance().reference.child("chats")
        val chatListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val chats = mutableListOf<ChatModel>()
                for (chatSnapshot in snapshot.children) {
                    val chatId = chatSnapshot.key.toString()
                    val messages = mutableListOf<MessageModel>()
                    for (messageSnapshot in chatSnapshot.child("messages").children) {
                        val messageId = messageSnapshot.key.toString()
                        val senderId = messageSnapshot.child("senderId").value.toString()
                        val recipientId = messageSnapshot.child("recipientId").value.toString()
                        val timestamp = messageSnapshot.child("timestamp").value.toString().toLong()
                        val content = messageSnapshot.child("content").value.toString()
                        val message = MessageModel(messageId, senderId, recipientId, timestamp, content)
                        messages.add(message)
                    }
                    messages.sortBy { it.timestamp } // Sort the messages based on timestamp
                    val chat = ChatModel(chatId, messages)
                    chats.add(chat)
                }
                chats.sortBy { it.messages.last().timestamp } // Sort the chats based on the latest message timestamp
                chatList.value = chats
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        }
        chatRef.addValueEventListener(chatListener)
        return chatList
    }




}