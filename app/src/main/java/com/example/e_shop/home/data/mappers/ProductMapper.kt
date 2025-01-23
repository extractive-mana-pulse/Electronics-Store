package com.example.e_shop.home.data.mappers

import com.example.e_shop.home.data.local.ProductsEntity
import com.example.e_shop.home.domain.model.ProductImages
import com.example.e_shop.home.domain.model.Products
import com.example.e_shop.home.domain.model.Specifications

class ProductMapper {

    fun Products.toProductsEntity(): ProductsEntity {
        return ProductsEntity(
//            specifications = Specifications(),
//            productImages = ProductImages(),
            _id = _id,
            name = name,
            brand = brand,
            price = price,
//            colors = colors,
            image = image,
            description = description,
            category = category,
            featured = featured,
            shipping = shipping,
//            features = features,
            url = url,
            rating = rating,
            _v = _v,
            id = 0
        )
    }

    fun ProductsEntity.toProduct(): Products {
        return Products(
//            specifications = Specifications(),
//            productImages = ProductImages(),
            _id = _id,
            name = name,
            brand = brand,
            price = price,
//            colors = colors,
            image = image,
            description = description,
            category = category,
            featured = featured,
            shipping = shipping,
//            features = features,
            url = url,
            rating = rating,
            _v = _v
        )
    }
}