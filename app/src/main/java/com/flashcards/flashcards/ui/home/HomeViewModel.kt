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
     * RxJava
     */
//    fun observeVocabularies(): LiveData<List<Vocabulary>> {
//        if (mVocabularies == null) {
//            mVocabularies = MediatorLiveData()
//
//            disposable.add(iService.getAllVocabularies()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnError {
//                    Log.e("HomeViewModel", "Something went wrong!")
//                }
//                .subscribeWith(object : DisposableObserver<List<Vocabulary>>() {
//                    override fun onComplete() {
//                        Log.d("HomeViewModel", "Complete")
//                    }
//
//                    override fun onNext(value: List<Vocabulary>) {
//                        Log.d("HomeViewModel", value.toString())
//                        mVocabularies!!.value = value
//                    }
//
//                    override fun onError(e: Throwable) {
//                        e.printStackTrace()
//                        Log.e("HomeViewModel", "Error!!!")
//                    }
//                })
//            )
//        }
//        return mVocabularies!!
//    }

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
