package com.example.dvs.remote.response

import com.google.gson.annotations.SerializedName

data class PartnerResponse(

	@field:SerializedName("PartnerResponse")
	val partnerResponse: List<PartnerResponseItem?>? = null
)

data class PartnerResponseItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("is_company")
	val isCompany: Boolean? = null,

	@field:SerializedName("email")
	val email: String? = null
)
