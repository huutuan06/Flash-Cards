package com.flashcards.flashcards.ui.home

import android.util.Log
import com.flashcards.flashcards.base.BaseViewModel
import com.flashcards.flashcards.service.repository.IService
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(): BaseViewModel() {

    @Inject
    lateinit var iService: IService

    fun getAllVocabularies(){
        iService.getAllVocabularies()
    }
}