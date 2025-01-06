package com.example.e_shop.home.domain.model

import com.google.gson.annotations.SerializedName

data class Specifications (

  @SerializedName("processor" ) var processor : String? = null,
  @SerializedName("cpu"       ) var cpu       : String? = null,
  @SerializedName("gpu"       ) var gpu       : String? = null,
  @SerializedName("ram"       ) var ram       : String? = null,
  @SerializedName("storage"   ) var storage   : String? = null,
  @SerializedName("display"   ) var display   : String? = null

)