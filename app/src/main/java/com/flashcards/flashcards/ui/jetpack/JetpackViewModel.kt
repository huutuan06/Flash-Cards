package com.flashcards.flashcards.ui.jetpack

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.flashcards.flashcards.base.BaseViewModel
import com.flashcards.flashcards.database.MainDatabase
import com.flashcards.flashcards.database.entities.Vocabulary
import com.flashcards.flashcards.api.repository.IServiceCoroutines
import com.flashcards.flashcards.ui.flashcard.FlashCardViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class JetpackViewModel @Inject constructor(
    private val iServiceCoroutines: IServiceCoroutines,
    private val mainDatabase: MainDatabase
) : BaseViewModel() {

    sealed class Event {
        data class Error(val throwable: Throwable) : Event()
    }

    private val eventAction = PublishSubject.create<Event>()
    val observableAction: Observable<Event> = eventAction.hide()

    val randomVoca = MutableLiveData<Vocabulary>()
    val randomVoca1 = MutableLiveData<Vocabulary>()
    val randomVoca2 = MutableLiveData<Vocabulary>()

    val isLoading = MutableLiveData(false)

    suspend fun getRandomVocabularyFromServer() {
        isLoading.value = true

        var result: Vocabulary

        try {
            withContext(Dispatchers.IO) {
                result = iServiceCoroutines.getAllVocabulariesAsync().await().random()
            }

            withContext(Dispatchers.Main) {
                randomVoca.value = result
                isLoading.value = false
            }
        } catch (throwable: Throwable) {
            isLoading.value = false
            eventAction.onNext(Event.Error(throwable))
        }
    }

    fun onClick1() {
        var result: Vocabulary
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                result = iServiceCoroutines.getAllVocabulariesAsync().await().random()
            }

            withContext(Dispatchers.Main) {
                randomVoca1.value = result
            }
        }
    }

    fun onClick2() {
        var result: Vocabulary
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                result = mainDatabase.vocabularyDAO().getAllVocabularies().random()
            }
            withContext(Dispatchers.Main) {
                randomVoca2.value = result
            }
        }
    }
}
