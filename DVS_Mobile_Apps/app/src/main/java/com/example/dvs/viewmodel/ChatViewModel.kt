package com.example.dvs.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dvs.model.UserPreference
import com.example.dvs.remote.param.NotificationParam

import com.example.dvs.remote.response.NotificationResponse
import com.example.dvs.remote.retrofit.ApiConfig
import com.example.dvs.ui.splashscreen.dataStore
import com.example.dvs.util.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatViewModel(context: Context): ViewModel() {
    private val apiService = ApiConfig.getApiServiceChat()
    val userPreference = UserPreference.getInstance(context.dataStore)

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


}