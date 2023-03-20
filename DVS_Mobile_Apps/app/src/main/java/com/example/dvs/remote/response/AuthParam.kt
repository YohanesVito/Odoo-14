package com.example.dvs.remote.response

import com.google.gson.annotations.SerializedName

data class AuthParam(

	@field:SerializedName("access_token")
	val accessToken: String? = null,

	@field:SerializedName("provider")
	val provider: String? = null,

	@field:SerializedName("oauth_uid")
	val oauth_uid: String? = null
)
