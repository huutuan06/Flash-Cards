package com.flashcards.flashcards.ui.home

import android.util.Log
import com.flashcards.flashcards.base.BaseViewModel
import com.flashcards.flashcards.service.repository.IService
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel : BaseViewModel() {

    @Inject
    lateinit var service: IService

    fun getAllVocabularies(){
//        disposable.add(service.getAllVocabularies()
//            .subscribeOn(Schedulers.newThread())
//            .doOnComplete{
//                Log.d("Complete", "Complete")
//            }
//            .subscribe())

    }
}