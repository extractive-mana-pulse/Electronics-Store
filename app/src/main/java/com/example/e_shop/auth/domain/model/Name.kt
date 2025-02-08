package com.example.e_shop.auth.domain.model

import com.google.gson.annotations.SerializedName


data class Name (

  @SerializedName("firstname" ) var firstname : String? = null,
  @SerializedName("lastname"  ) var lastname  : String? = null

)