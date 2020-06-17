package com.flashcards.flashcards.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.flashcards.flashcards.base.BaseViewModel
import com.flashcards.flashcards.service.model.Vocabulary
import com.flashcards.flashcards.service.repository.IService
import com.flashcards.flashcards.util.applyIOScheduler
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class HomeViewModel @Inject constructor(private var iService: IService) : BaseViewModel() {

    sealed class Event {
        data class Error(val throwable: Throwable) : Event()
    }

    var listVocabularies = MutableLiveData<List<Vocabulary>>()
    val isLoading = MutableLiveData(false)

    val word = MutableLiveData<Vocabulary>()

    private val eventAction = PublishSubject.create<Event>()
    val observableAction : Observable<Event> = eventAction.hide()

    init {
        disposable.add(iService.getAllVocabularies()
            .applyIOScheduler()
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
                eventAction.onNext(Event.Error(it))
            })
        )
    }

    fun onWordSelected(item: Vocabulary) {
        word.value = item
    }
}
