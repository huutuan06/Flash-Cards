package com.flashcards.flashcards.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flashcards.flashcards.database.dao.VocabularyDao
import com.flashcards.flashcards.database.entities.Vocabulary

@Database(entities = [Vocabulary::class], version = 1, exportSchema = false)
abstract class MainDatabase : RoomDatabase() {

    abstract fun vocabularyDAO() : VocabularyDao
}
