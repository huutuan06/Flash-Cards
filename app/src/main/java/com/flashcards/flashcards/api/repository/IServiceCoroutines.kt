package com.flashcards.flashcards.api.repository

import com.flashcards.flashcards.database.entities.Vocabulary
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface IServiceCoroutines {

    @GET("api/cards")
    fun getAllVocabulariesAsync() : Deferred<List<Vocabulary>>
}
