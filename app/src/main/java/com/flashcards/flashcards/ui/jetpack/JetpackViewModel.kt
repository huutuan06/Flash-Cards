package com.flashcards.flashcards.ui.jetpack

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.flashcards.flashcards.base.BaseViewModel
import com.flashcards.flashcards.service.model.Vocabulary
import com.flashcards.flashcards.service.repository.IServiceCoroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class JetpackViewModel @Inject constructor(
    private val iServiceCoroutines: IServiceCoroutines
) : BaseViewModel() {

    val randomVoca = MutableLiveData<Vocabulary>()
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
}
