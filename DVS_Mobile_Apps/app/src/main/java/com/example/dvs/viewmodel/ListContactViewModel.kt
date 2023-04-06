package com.example.dvs.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dvs.model.NewContactModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ListContactViewModel(context: Context): ViewModel() {

    fun getContacts(): MutableLiveData<ArrayList<NewContactModel>>{
        val listUser = MutableLiveData<ArrayList<NewContactModel>>()
        val db = Firebase.firestore
        val usersCollection = db.collection("DVS-User")

        usersCollection.get()
            .addOnSuccessListener { documents ->
                val users = ArrayList<NewContactModel>()
                for (document in documents) {
                    Log.d("Firestore", "${document.id} => ${document.data}")
                    // Create a new UserModel object from the document data
                    val user = NewContactModel(
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