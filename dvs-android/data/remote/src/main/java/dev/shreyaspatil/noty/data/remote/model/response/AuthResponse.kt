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

package dev.shreyaspatil.noty.data.remote.model.response

//data class AuthResponse(
//    override val status: State,
//    override val message: String,
//    val token: String?
//) : BaseResponse

//data class AuthResponse(
//    override val status: State.SUCCESS,
//    override val message: String,
////    val token: String?,
//    val access_token: String? = null,
//    val token_type: String? = null,
//
//    ) : BaseResponse

data class AuthResponse(
    val status: String,
    override val message: String,
    val access_token: String,
    val token_type: String,
) : BaseResponse2
//{
//    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY3ODkyNTY2Mn0.tX72P62gTXgbJFogNn1CYhMtB1MkOQn0env0-JWdDOo",
//    "token_type": "bearer",
//    "status": "SUCCESS",
//    "message": "Login successful"
//}

