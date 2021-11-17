package com.example.mydictionary.feature_dictionary.data.remote.dto

import com.example.mydictionary.feature_dictionary.domain.model.WordInfo

data class WordInfoDto(
    val meanings: List<MeaningDto>,
    val origin: String,
    val phonetic: String,
    val phonetics: List<PhoneticDto>,
    val word: String
) {
    fun toWordInfo(): WordInfo {
        return WordInfo(
            meanings = meanings.map { it.toMeaning() },
            origin = origin,
            phonetic = phonetic,
            word = word
        )
    }
}