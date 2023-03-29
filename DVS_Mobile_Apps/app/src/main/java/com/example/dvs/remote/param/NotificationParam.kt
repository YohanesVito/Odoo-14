package com.example.dvs.remote.param

import com.google.gson.annotations.SerializedName

data class NotificationParam(

	@field:SerializedName("notification")
	val notification: Notification? = null,

	@field:SerializedName("to")
	val to: String? = null
)

data class Notification(

	@field:SerializedName("subtitle")
	val subtitle: String? = null,

	@field:SerializedName("body")
	val body: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)
