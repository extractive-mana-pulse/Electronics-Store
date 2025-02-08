package com.example.e_shop.auth.domain.model

import com.google.gson.annotations.SerializedName


data class Geolocation (

  @SerializedName("lat"  ) var lat  : String? = null,
  @SerializedName("long" ) var long : String? = null

)