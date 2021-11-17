package com.example.mydictionary.feature_dictionary.data.local

import androidx.room.Database
import androidx.room.TypeConverters

@Database(
    entities = [WordInfoEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class WordInfoDatabase {

    abstract val wordInfoDao: WordInfoDao

}