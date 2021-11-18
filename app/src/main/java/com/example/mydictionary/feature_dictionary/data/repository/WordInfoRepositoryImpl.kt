package com.example.mydictionary.feature_dictionary.data.repository

import com.example.mydictionary.core.util.Resource
import com.example.mydictionary.feature_dictionary.data.local.WordInfoDao
import com.example.mydictionary.feature_dictionary.data.remote.DictionaryAPI
import com.example.mydictionary.feature_dictionary.domain.model.WordInfo
import com.example.mydictionary.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class WordInfoRepositoryImpl(
    private val api: DictionaryAPI,
    private val dao: WordInfoDao
) : WordInfoRepository {

    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Loading())

        val wordInfo = dao.getWordInfo(word).map {
            it.toWordInfo()
        }
        emit(Resource.Loading(data = wordInfo))

        //make the network request
        try {
            val remoteWordInfo = api.getWordInfo(word)
            dao.deleteWordInfo(remoteWordInfo.map {
                it.word
            })
            dao.insertWordInfo(remoteWordInfo.map {
                it.toWordInfoEntity()
            })
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    "Oops, something went wrong...",
                    data = wordInfo
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    "Couldn't reach the server...",
                    data = wordInfo
                )
            )
        }

        val newWordInfo = dao.getWordInfo(word).map {
            it.toWordInfo()
        }
        emit(Resource.Success(newWordInfo))

    }
}