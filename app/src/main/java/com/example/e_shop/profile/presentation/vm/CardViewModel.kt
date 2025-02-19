package com.example.e_shop.profile.presentation.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_shop.profile.domain.model.CardInfo
import com.example.e_shop.profile.domain.repository.CardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(private val repository: CardRepository): ViewModel() {

    private val _cards = MutableStateFlow<List<CardInfo>>(emptyList())
    val cards: StateFlow<List<CardInfo>> = _cards

    private val _insertStatus = MutableStateFlow<CardInfo?>(null)
    val insertStatus: StateFlow<CardInfo?> = _insertStatus

    init {
        getCards()
    }

    fun getCards(): Job {
        return viewModelScope.launch {
            try {
                val cardsList = repository.getCards()
                Log.d("CardViewModel", "Fetched cards: $cardsList")
                _cards.value = cardsList
            } catch (e: Exception) {
                Log.e("CardViewModel", "Error fetching cards: ${e.message}")
                _cards.value = emptyList()
            }
        }
    }

    fun insertCard(cardInfo: CardInfo) = viewModelScope.launch {
        try {
            repository.insertCard(cardInfo)
            _insertStatus.value = cardInfo
            getCards()
            Log.d("CardViewModel", "Fetched cards: $cardInfo")
        } catch (e: Exception) {
            Log.e("CardViewModel", "Error fetching cards: ${e.message}")
        }
    }

    fun deleteCard(cardInfo: CardInfo) = viewModelScope.launch {
        try {
            repository.deleteCard(cardInfo)
            getCards() // Refresh the list after deletion
        } catch (e: Exception) {
            // Handle error
        }
    }
}