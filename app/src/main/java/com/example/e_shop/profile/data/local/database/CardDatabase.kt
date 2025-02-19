package com.example.e_shop.profile.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.e_shop.profile.domain.model.CardInfo

@Database(entities = [CardInfo::class], version = 1)
abstract class CardDatabase: RoomDatabase() { abstract val dao: CardDao }