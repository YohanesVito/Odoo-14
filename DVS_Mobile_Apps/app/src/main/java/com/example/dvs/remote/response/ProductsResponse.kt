package com.example.dvs.remote.response

import com.google.gson.annotations.SerializedName

data class ProductsResponse(

	@field:SerializedName("ProductsResponse")
	val productsResponse: List<ProductsResponseItem>
)

data class ProductsResponseItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
