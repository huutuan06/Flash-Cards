package com.flashcards.flashcards.ui.flashcard

import androidx.lifecycle.MutableLiveData
import com.flashcards.flashcards.base.BaseViewModel
import com.flashcards.flashcards.service.model.Vocabulary
import com.flashcards.flashcards.service.repository.IService
import com.flashcards.flashcards.util.applyIOScheduler
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

class FlashCardViewModel @Inject constructor(val iService: IService) : BaseViewModel() {

    sealed class Event {
        data class Error(val throwable: Throwable) : Event()
    }

    var listVocabularies = MutableLiveData<List<Vocabulary>>()
    val isLoading = MutableLiveData(false)

    val word = MutableLiveData<Vocabulary>()

    private val eventAction = PublishSubject.create<Event>()
    val observableAction: Observable<Event> = eventAction.hide()

    init {
        getVocabularies()
    }

    fun getVocabularies() {
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
                Timber.e(it.message.toString())
                eventAction.onNext(Event.Error(it))
            })
        )
    }

    fun onWordSelected(item: Vocabulary) {
        word.value = item
    }
}
