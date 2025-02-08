package com.example.e_shop.auth.domain.model

import com.google.gson.annotations.SerializedName


data class Address (

  @SerializedName("geolocation" ) var geolocation : Geolocation? = Geolocation(),
  @SerializedName("city"        ) var city        : String?      = null,
  @SerializedName("street"      ) var street      : String?      = null,
  @SerializedName("number"      ) var number      : Int?         = null,
  @SerializedName("zipcode"     ) var zipcode     : String?      = null

)