package com.example.e_shop.home.domain.model

import com.google.gson.annotations.SerializedName

data class Root (

  @SerializedName("count"    ) var count    : Int?                = null,
  @SerializedName("limit"    ) var limit    : Int?                = null,
  @SerializedName("page"     ) var page     : Int?                = null,
  @SerializedName("Products")  var products : ArrayList<Products>

)