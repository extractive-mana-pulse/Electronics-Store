package com.example.e_shop.core.di

import android.content.Context
import androidx.room.Room
import com.example.e_shop.profile.data.local.database.CardDao
import com.example.e_shop.profile.data.local.database.CardDatabase
import com.example.e_shop.profile.data.local.repositoryImpl.CardRepositoryImpl
import com.example.e_shop.profile.domain.repository.CardRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun provideCardDatabase(@ApplicationContext context: Context): CardDatabase {
        return Room.databaseBuilder(
            context,
            CardDatabase::class.java,
            "card.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCardDao(database: CardDatabase) = database.dao

    @Provides
    @Singleton
    fun provideCardRepository(dao: CardDao): CardRepository = CardRepositoryImpl(dao)
}