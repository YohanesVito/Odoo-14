package com.example.dvs.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dvs.remote.retrofit.ApiConfig
import com.example.dvs.remote.response.LoginResponse
import com.example.dvs.model.UserPreference
import com.example.dvs.ui.splashscreen.dataStore
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
                                token = responseBody.accessToken!!,
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
}