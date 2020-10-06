package com.flashcards.flashcards.ui.flashcard

import androidx.lifecycle.MutableLiveData
import com.flashcards.flashcards.base.BaseViewModel
import com.flashcards.flashcards.database.MainDatabase
import com.flashcards.flashcards.database.entities.Vocabulary
import com.flashcards.flashcards.service.repository.IService
import com.flashcards.flashcards.util.applyIOScheduler
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

class FlashCardViewModel @Inject constructor(
    private val iService: IService,
    private val mainDatabase: MainDatabase
) : BaseViewModel() {

    sealed class Event {
        data class Error(val throwable: Throwable) : Event()
    }

    var listVocabularies = MutableLiveData<List<Vocabulary>>()
    val isLoading = MutableLiveData(false)

    val word = MutableLiveData<Vocabulary>()

    private val eventAction = PublishSubject.create<Event>()
    val observableAction: Observable<Event> = eventAction.hide()

    init {
        getVocabulariesAndSaveData()
    }

    fun getVocabulariesAndSaveData() {
        disposable.add(iService.getAllVocabularies()
            .applyIOScheduler()
            .doOnSubscribe {
                isLoading.value = true
            }
            .doFinally {
                isLoading.value = false
            }
            .subscribe({
                listVocabularies.apply {
                    this.value = it.toMutableList()
                    saveData(this.value)
                }
            }, {
                Timber.e(it.message.toString())
                eventAction.onNext(Event.Error(it))
            })
        )
    }

    private fun saveData(listVocabulary: List<Vocabulary>?) {
        disposable.add(mainDatabase.vocabularyDAO().insertData(listVocabulary)
            .applyIOScheduler()
            .subscribe())
    }

    fun onWordSelected(item: Vocabulary) {
        word.value = item
    }
}
