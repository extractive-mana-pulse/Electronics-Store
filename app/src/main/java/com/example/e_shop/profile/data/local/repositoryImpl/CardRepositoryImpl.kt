package com.example.e_shop.profile.data.local.repositoryImpl

import com.example.e_shop.profile.data.local.database.CardDao
import com.example.e_shop.profile.domain.model.CardInfo
import com.example.e_shop.profile.domain.repository.CardRepository

class CardRepositoryImpl(private val dao: CardDao) : CardRepository {

    override suspend fun getCards(): List<CardInfo> {
        return dao.getCards()
    }

//    private val storedCards = MutableStateFlow(CardInfo())

    override suspend fun insertCard(cardInfo: CardInfo) {
        return dao.insertCard(cardInfo)
//        storedCards.value.copy(
//            cvv = cardInfo.cvv,
//            number = cardInfo.number,
//            cardHolderName = cardInfo.cardHolderName,
//            expiryDate = cardInfo.expiryDate,
//            isDefault = cardInfo.isDefault
//        )
    }

    override suspend fun deleteCard(cardInfo: CardInfo) {
        return dao.deleteCard(cardInfo)
    }
}