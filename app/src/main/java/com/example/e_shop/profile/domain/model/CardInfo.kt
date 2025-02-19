package com.example.e_shop.profile.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cardinfo")
data class CardInfo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val number: Int?,
    val expiryDate: String?,
    val cvv: Int?,
    val cardHolderName: String?,
    val isDefault: Boolean?
)
