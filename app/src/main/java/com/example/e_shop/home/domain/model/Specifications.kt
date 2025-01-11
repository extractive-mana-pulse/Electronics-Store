package com.example.e_shop.home.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Specifications (

  val processor : String? = null,
  val cpu       : String? = null,
  val gpu       : String? = null,
  val ram       : String? = null,
  val storage   : String? = null,
  val display   : String? = null

)