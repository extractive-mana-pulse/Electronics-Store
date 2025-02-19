package com.example.e_shop.profile.domain.repository

import com.example.e_shop.profile.domain.model.CardInfo

interface CardRepository {

    suspend fun getCards(): List<CardInfo>

    suspend fun insertCard(cardInfo: CardInfo)

    suspend fun deleteCard(cardInfo: CardInfo)
}