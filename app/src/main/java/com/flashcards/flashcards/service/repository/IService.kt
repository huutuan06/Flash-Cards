package com.flashcards.flashcards.service.repository

import com.flashcards.flashcards.database.entities.Vocabulary
import io.reactivex.Observable
import retrofit2.http.GET

interface IService {

    @GET("api/cards")
    fun getAllVocabularies() : Observable<List<Vocabulary>>
}
