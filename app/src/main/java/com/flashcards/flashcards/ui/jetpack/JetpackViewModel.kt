package com.flashcards.flashcards.ui.jetpack

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.flashcards.flashcards.base.BaseViewModel
import com.flashcards.flashcards.database.MainDatabase
import com.flashcards.flashcards.database.entities.Vocabulary
import com.flashcards.flashcards.service.repository.IServiceCoroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class JetpackViewModel @Inject constructor(
    private val iServiceCoroutines: IServiceCoroutines,
    private val mainDatabase: MainDatabase
) : BaseViewModel() {

    val randomVoca = MutableLiveData<Vocabulary>()
    val randomVoca1 = MutableLiveData<Vocabulary>()
    val randomVoca2 = MutableLiveData<Vocabulary>()

    val isLoading = MutableLiveData(false)

    @SuppressLint("CheckResult")
    suspend fun getRandomVocabularyFromServer() {
        isLoading.value = true

        var result: Vocabulary

        withContext(Dispatchers.IO) {
            result = iServiceCoroutines.getAllVocabulariesAsync().await().random()
        }

        withContext(Dispatchers.Main) {
            randomVoca.value = result
            isLoading.value = false
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
