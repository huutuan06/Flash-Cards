package com.flashcards.flashcards.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.MediatorLiveData
import com.flashcards.flashcards.base.BaseViewModel
import com.flashcards.flashcards.service.model.Vocabulary
import com.flashcards.flashcards.service.repository.IService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(private var iService: IService) : BaseViewModel() {

//    var mVocabularies: MediatorLiveData<List<Vocabulary>>? = null

    private var vocabularyReponseMessagge : MediatorLiveData<List<Vocabulary>>? = null

    private var errorReponseMessage: MediatorLiveData<String>? = null

    fun getVocabularyReponseMessagge() : MediatorLiveData<List<Vocabulary>>? = vocabularyReponseMessagge

    fun getErrorReponseMessage() : MediatorLiveData<String>? = errorReponseMessage

    @SuppressLint("CheckResult")
    fun getVocabulary() {
        iService.getAllVocabularies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<Vocabulary>>() {
                override fun onComplete() {
                }

                override fun onNext(value: List<Vocabulary>) {
                    getVocabularyReponseMessagge()?.value = value
                }

                override fun onError(e: Throwable) {
                    getErrorReponseMessage()?.value = e.message.toString()
                }
            })
    }
}
