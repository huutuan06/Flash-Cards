package com.flashcards.flashcards.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import com.flashcards.flashcards.base.BaseViewModel
import com.flashcards.flashcards.service.model.Vocabulary
import com.flashcards.flashcards.service.repository.IService
import javax.inject.Inject

class HomeViewModel @Inject constructor(private var iService: IService) : BaseViewModel() {

    var mVocabularies: MediatorLiveData<List<Vocabulary>>? = null

    /**
     * Coroutines
     */
    val data: LiveData<List<Vocabulary>> = liveData {
        val receivedData = observeVocabularies()
        emit(receivedData.value!!)
    }

    private suspend fun observeVocabularies(): LiveData<List<Vocabulary>> {
        if (mVocabularies == null) {
            mVocabularies = MediatorLiveData()

            mVocabularies!!.value = iService.getAllVocabulariesAsync().await()
//            mVocabularies!!.postValue(iService.getAllVocabulariesAsync().await())
        }
        return mVocabularies!!
    }
}
