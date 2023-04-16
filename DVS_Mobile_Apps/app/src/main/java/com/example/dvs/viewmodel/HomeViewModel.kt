package com.example.dvs.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.dvs.model.UserModel
import com.example.dvs.model.UserPreference
import com.example.dvs.remote.param.NotificationParam

import com.example.dvs.remote.response.NotificationResponse
import com.example.dvs.remote.retrofit.ApiConfig
import com.example.dvs.ui.splashscreen.dataStore
import com.example.dvs.util.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(context: Context): ViewModel() {
    private val apiService = ApiConfig.getApiServiceChat()
    val userPreference = UserPreference.getInstance(context.dataStore)

    fun getUser(): LiveData<UserModel> = userPreference.getUser().asLiveData()
}