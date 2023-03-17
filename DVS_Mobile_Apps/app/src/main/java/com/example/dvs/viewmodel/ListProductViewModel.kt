package com.example.dvs.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.dvs.remote.retrofit.ApiConfig
import com.example.dvs.model.UserModel
import com.example.dvs.model.UserPreference
import com.example.dvs.remote.response.ProductsResponseItem
import com.example.dvs.ui.splashscreen.dataStore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.dvs.util.Result

class ListProductViewModel(context: Context): ViewModel(){
    private val apiService = ApiConfig.getApiService()
    private val userPreference = UserPreference.getInstance(context.dataStore)

     fun getProductsRequest(token: String, search: String, offset: Int, limit: Int): LiveData<ArrayList<ProductsResponseItem>> {
        val result = MutableLiveData<ArrayList<ProductsResponseItem>>()
        apiService.getProducts(token, search, offset, limit).enqueue(object : Callback<List<ProductsResponseItem>> {
            override fun onResponse(
                call: Call<List<ProductsResponseItem>>,
                response: Response<List<ProductsResponseItem>>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null){
                        result.value = response.body() as ArrayList<ProductsResponseItem>
                    }
                }
            }
            override fun onFailure(call: Call<List<ProductsResponseItem>>, t: Throwable) {
                Log.d("Retrofit Error",t.message.toString())
            }
        })
        return result
    }

    fun getUser(): LiveData<UserModel> {
        return userPreference.getUser().asLiveData()
    }

}