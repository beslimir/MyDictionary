package com.example.mydictionary.feature_dictionary.domain.use_case

import com.example.mydictionary.core.util.Resource
import com.example.mydictionary.feature_dictionary.domain.model.WordInfo
import com.example.mydictionary.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

//validation logic

class GetWordInfoUseCase(
    private val repository: WordInfoRepository
) {

    operator fun invoke(word: String): Flow<Resource<List<WordInfo>>> {
        if (word.isBlank()) {
            return flow {  }
        }

        return repository.getWordInfo(word)
    }

}