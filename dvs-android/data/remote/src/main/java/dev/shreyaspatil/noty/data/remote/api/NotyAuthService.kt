/*
 * Copyright 2020 Shreyas Patil
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.shreyaspatil.noty.data.remote.api

import dev.shreyaspatil.noty.data.remote.model.request.AuthRequest
import dev.shreyaspatil.noty.data.remote.model.response.AuthResponse
import dev.shreyaspatil.noty.data.remote.model.response.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST

interface NotyAuthService {

    @POST("/auth/register")
    suspend fun register(@Body authRequest: AuthRequest): Response<AuthResponse>

    @POST("/auth/login")
    suspend fun login(@Body authRequest: AuthRequest): Response<AuthResponse>

    @POST("/token")
    suspend fun getToken(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Response<AuthResponse>

    @POST("/auth/login")
    suspend fun getToken2(@Body authRequest: AuthRequest): Response<AuthResponse>
}