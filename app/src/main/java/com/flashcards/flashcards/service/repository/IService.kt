package com.flashcards.flashcards.service.repository

import com.flashcards.flashcards.service.model.Vocabulary
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface IService {
    @GET("api/cards")
    fun getAllVocabularies() : Observable<List<Vocabulary>>
}
