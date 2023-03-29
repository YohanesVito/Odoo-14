package com.example.dvs.remote.retrofit

import com.example.dvs.remote.param.AuthParam
import com.example.dvs.remote.param.NotificationParam
import com.example.dvs.remote.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("/token")
    fun getToken(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Call<LoginResponse>

    @FormUrlEncoded
    @GET("/partners")
    fun getPartner(
        @Header("Authorization") token: String,
        @Field("is_company") is_company: Boolean,
        @Field("limit") limit: Int,
    ): Call<PartnerResponse>

    @Headers("Accept: application/json")
    @GET("/products")
    fun getProducts(
        @Header("Authorization") token: String,
        @Query("search") search: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): Call<List<ProductsResponseItem>>

    @Headers("Content-Type: application/json")
    @POST("/auth/login")
    fun signInToOdoo(@Body body: AuthParam): Call<LoginResponse>

    @Headers(
        "Content-Type: application/json",
        "Authorization: key=AAAAvT_TUiM:APA91bHvSougEn-VjftqSw-NTC4fn8HFyzDmJbdfvuP51OKu6geRL-GRrzhMd_hS7TChZd8MtJ94_leEDIX6KkFmDTrp29PLs6Ub51IgX5rt5aAiDi498AazxdiP2FxX9PTFX4Csu_5X"
    )
    @POST("/fcm/send")
    fun sendNotification(
        @Body body: NotificationParam): Call<NotificationResponse>

}