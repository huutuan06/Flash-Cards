package com.flashcards.flashcards.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import com.flashcards.flashcards.base.BaseViewModel
import com.flashcards.flashcards.service.model.Vocabulary
import com.flashcards.flashcards.service.repository.IService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class HomeViewModel @Inject constructor(private var iService: IService) : BaseViewModel() {

    var mVocabularies: MediatorLiveData<List<Vocabulary>>? = null
//
//    fun getAllVocabularies() {
//        disposable.add(iService.getAllVocabularies()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnError {
//                Log.e("HomeViewModel", "Something went wrong!")
//            }
//            .subscribeWith(object : DisposableObserver<List<Vocabulary>>() {
//                override fun onComplete() {
//                    Log.d("HomeViewModel", "Complete")
//                }
//
//                override fun onNext(value: List<Vocabulary>) {
//                    Log.d("HomeViewModel", value.toString())
//                }
//
//                override fun onError(e: Throwable) {
//                    e.printStackTrace()
//                    Log.e("HomeViewModel", "Error!!!")
//                }
//            })
//        )
//    }

    fun observeVocabularies(): LiveData<List<Vocabulary>> {
        if (mVocabularies == null) {
            mVocabularies = MediatorLiveData()

            disposable.add(iService.getAllVocabularies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    Log.e("HomeViewModel", "Something went wrong!")
                }
                .subscribeWith(object : DisposableObserver<List<Vocabulary>>() {
                    override fun onComplete() {
                        Log.d("HomeViewModel", "Complete")
                    }

                    override fun onNext(value: List<Vocabulary>) {
                        Log.d("HomeViewModel", value.toString())
                        mVocabularies!!.value = value
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        Log.e("HomeViewModel", "Error!!!")
                    }
                })
            )
//            val source: LiveData<List<Vocabulary>> = LiveDataReactiveStreams.fromPublisher {
//                iService.getAllVocabularies()
//                    .subscribeOn(Schedulers.io())
//            }
//            mVocabularies!!.addSource(source) { vocabularies ->
//                mVocabularies!!.value = vocabularies
//            }
        }
        return mVocabularies!!
    }
}
