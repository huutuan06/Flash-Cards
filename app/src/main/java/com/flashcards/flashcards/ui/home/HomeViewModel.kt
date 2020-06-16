package com.flashcards.flashcards.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.flashcards.flashcards.base.BaseViewModel
import com.flashcards.flashcards.service.model.Vocabulary
import com.flashcards.flashcards.service.repository.IService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(private var iService: IService) : BaseViewModel() {

    var listVocabularies = MutableLiveData<List<Vocabulary>>()
    val isLoading = MutableLiveData(false)

    init {
        disposable.add(iService.getAllVocabularies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isLoading.value = true
            }
            .doFinally {
                isLoading.value = false
            }
            .subscribe({
                listVocabularies.value = it.toMutableList()
            }, {
                Log.e("HomeViewModel:", it.message.toString())
            }))
    }
}
