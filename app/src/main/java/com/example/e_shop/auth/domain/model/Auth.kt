package com.example.e_shop.auth.domain.model

import com.google.gson.annotations.SerializedName


data class Auth (

  @SerializedName("address"  ) var address  : Address? = Address(),
  @SerializedName("id"       ) var id       : Int?     = null,
  @SerializedName("email"    ) var email    : String?  = null,
  @SerializedName("username" ) var username : String?  = null,
  @SerializedName("password" ) var password : String?  = null,
  @SerializedName("name"     ) var name     : Name?    = Name(),
  @SerializedName("phone"    ) var phone    : String?  = null,
  @SerializedName("__v"      ) var _v       : Int?     = null

)