package com.example.e_shop.catalog.domain.model

import com.google.gson.annotations.SerializedName

data class CategoryProductRating (

    @SerializedName("rate"  ) var rate  : Double? = null,
    @SerializedName("count" ) var count : Int?    = null

)
