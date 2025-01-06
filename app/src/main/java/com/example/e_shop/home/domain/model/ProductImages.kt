package com.example.e_shop.home.domain.model

import com.google.gson.annotations.SerializedName


data class ProductImages (

  @SerializedName("pi_1" ) var pi1 : String? = null,
  @SerializedName("pi_2" ) var pi2 : String? = null,
  @SerializedName("pi_3" ) var pi3 : String? = null,
  @SerializedName("pi_4" ) var pi4 : String? = null

)