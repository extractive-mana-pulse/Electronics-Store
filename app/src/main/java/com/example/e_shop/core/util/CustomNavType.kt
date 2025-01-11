package com.example.e_shop.core.util

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.example.e_shop.home.domain.model.ProductImages
import com.example.e_shop.home.domain.model.Specifications
import kotlinx.serialization.json.Json

object CustomNavType {

    val specType = object : NavType<Specifications>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): Specifications? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): Specifications {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: Specifications): String {
            return Uri.encode(Json.encodeToString(Specifications.serializer(), value))
        }

        override fun put(bundle: Bundle, key: String, value: Specifications) {
            bundle.putString(key, Json.encodeToString(Specifications.serializer(), value))
        }
    }

    val picType = object : NavType<ProductImages>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): ProductImages? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): ProductImages {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: ProductImages): String {
            return Uri.encode(Json.encodeToString(ProductImages.serializer(), value))
        }

        override fun put(bundle: Bundle, key: String, value: ProductImages) {
            bundle.putString(key, Json.encodeToString(ProductImages.serializer(), value))
        }
    }
}