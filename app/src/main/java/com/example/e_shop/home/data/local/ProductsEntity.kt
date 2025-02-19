package com.example.e_shop.home.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "products")
data class ProductsEntity(

    @PrimaryKey
    @SerializedName("id"             ) var id             : Int,
//    @SerializedName("specifications" ) val specifications : Specifications   = Specifications(),
//    @SerializedName("productImages"  ) var productImages  : ProductImages    = ProductImages(),
    @SerializedName("_id"            ) var _id            : String           = "",
    @SerializedName("name"           ) var name           : String?           = null,
    @SerializedName("brand"          ) var brand          : String?           = null,
    @SerializedName("price"          ) var price          : Int?              = null,
//    @SerializedName("colors"         ) var colors         : ArrayList<String> = arrayListOf(),
    @SerializedName("image"          ) var image          : String?           = null,
    @SerializedName("description"    ) var description    : String?           = null,
    @SerializedName("category"       ) var category       : String?           = null,
    @SerializedName("featured"       ) var featured       : Boolean?          = null,
    @SerializedName("shipping"       ) var shipping       : Boolean?          = null,
//    @SerializedName("features"       ) var features       : ArrayList<String> = arrayListOf(),
    @SerializedName("url"            ) var url            : String?           = null,
    @SerializedName("rating"         ) var rating         : Double?           = null,
    @SerializedName("__v"            ) var _v             : Int?              = null

)
