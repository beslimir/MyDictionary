package com.example.mydictionary.feature_dictionary.di

import android.app.Application
import androidx.room.Room
import com.example.mydictionary.feature_dictionary.data.local.WordInfoDao
import com.example.mydictionary.feature_dictionary.data.local.WordInfoDatabase
import com.example.mydictionary.feature_dictionary.data.remote.DictionaryAPI
import com.example.mydictionary.feature_dictionary.data.remote.DictionaryAPI.Companion.BASE_URL
import com.example.mydictionary.feature_dictionary.data.repository.WordInfoRepositoryImpl
import com.example.mydictionary.feature_dictionary.data.util.GsonParser
import com.example.mydictionary.feature_dictionary.domain.repository.WordInfoRepository
import com.example.mydictionary.feature_dictionary.domain.use_case.GetWordInfoUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordInfoModule {

    @Singleton
    @Provides
    fun provideGetWordInfoUseCase(repository: WordInfoRepository): GetWordInfoUseCase {
        return GetWordInfoUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideWordInfoRepository(api: DictionaryAPI, db: WordInfoDatabase): WordInfoRepository {
        return WordInfoRepositoryImpl(api, db.wordInfoDao)
    }

    @Singleton
    @Provides
    fun provideWordInfoDatabase(app: Application): WordInfoDatabase = Room.databaseBuilder(
        app,
        WordInfoDatabase::class.java,
        "word_info_db"
    ).addTypeConverter(GsonParser(Gson()))
        .build()

    @Singleton
    @Provides
    fun provideDictionaryApi(): DictionaryAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DictionaryAPI::class.java)

}