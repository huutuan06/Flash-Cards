package com.flashcards.flashcards.service.repository

import com.flashcards.flashcards.service.model.Vocabulary
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface IServiceCoroutines {

    @GET("api/cards")
    fun getAllVocabulariesAsync() : Deferred<List<Vocabulary>>
}
