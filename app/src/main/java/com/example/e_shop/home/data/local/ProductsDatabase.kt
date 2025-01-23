package com.example.e_shop.home.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [ProductsEntity::class],
    version = 1
)
abstract class ProductsDatabase: RoomDatabase() {

    abstract val productsDao: ProductDao

}