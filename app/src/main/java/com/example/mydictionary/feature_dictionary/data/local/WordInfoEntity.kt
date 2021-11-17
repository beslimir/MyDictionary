package com.example.mydictionary.feature_dictionary.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mydictionary.feature_dictionary.domain.model.Meaning
import com.example.mydictionary.feature_dictionary.domain.model.WordInfo

@Entity
data class WordInfoEntity(
    @PrimaryKey val id: Int? = null,
    val word: String,
    val phonetic: String,
    val origin: String,
    val meanings: List<Meaning>
) {
    fun toWordInfo(): WordInfo {
        return WordInfo(
            meanings = meanings,
            word = word,
            origin = origin,
            phonetic = phonetic
        )
    }
}
