package com.flashcards.flashcards.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flashcards.flashcards.database.entities.Vocabulary
import io.reactivex.Completable

@Dao
interface VocabularyDao {

    @Query("SELECT * FROM vocabularies")
    suspend fun getAllVocabularies(): List<Vocabulary>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(listVocabulary: List<Vocabulary>?): Completable
}
