package com.flashcards.flashcards.ui.home

import android.util.Log
import com.flashcards.flashcards.base.BaseViewModel
import com.flashcards.flashcards.service.repository.IService
import retrofit2.Retrofit
import javax.inject.Inject

class HomeViewModel @Inject constructor(var iService: IService) : BaseViewModel() {

    fun getAllVocabularies() {
        iService.getAllVocabularies()
    }
}
