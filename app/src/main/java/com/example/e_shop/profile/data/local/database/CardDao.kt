package com.example.e_shop.profile.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.e_shop.profile.domain.model.CardInfo

@Dao
interface CardDao {

    @Query("SELECT * FROM cardinfo")
    suspend fun getCards(): List<CardInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(cardInfo: CardInfo)

    @Delete
    suspend fun deleteCard(cardInfo: CardInfo)
}