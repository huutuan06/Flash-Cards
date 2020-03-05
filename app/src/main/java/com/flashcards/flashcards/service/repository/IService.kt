package com.flashcards.flashcards.service.repository

import com.flashcards.flashcards.service.response.WordsResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface IService {
    @GET("https://shawn-movie-rental.herokuapp.com/api/cards")
    fun getAllVocabularies() : Observable<Response<WordsResponse>>
}