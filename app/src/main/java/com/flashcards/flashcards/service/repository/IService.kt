package com.flashcards.flashcards.service.repository

import com.flashcards.flashcards.service.model.Vocabulary
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface IService {

    /**
     *For RxJava
     */
//    @GET("api/cards")
//    fun getAllVocabularies() : Observable<List<Vocabulary>>

    /**
     *For Coroutines
     */
    @GET("api/cards")
    fun getAllVocabulariesAsync() : Deferred<List<Vocabulary>>
}