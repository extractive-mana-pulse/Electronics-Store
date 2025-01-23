package com.example.e_shop.home.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ProductDao {

    @Upsert
    suspend fun upsertAll(beers: List<ProductsEntity>)

    @Query("SELECT * FROM products")
    fun pagingSource(): PagingSource<Int, ProductsEntity>

    @Query("DELETE FROM products")
    suspend fun clearAll()

}