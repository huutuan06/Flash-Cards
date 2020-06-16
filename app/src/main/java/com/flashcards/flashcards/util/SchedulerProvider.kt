package com.flashcards.flashcards.util

import com.flashcards.flashcards.util.SchedulerProvider.LongExecution
import com.flashcards.flashcards.util.SchedulerProvider.QuickExecution
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object SchedulerProvider {
    val QuickExecution = Schedulers.computation()
    val LongExecution = Schedulers.io()
}

fun Completable.applyComputationScheduler(): Completable {
    return subscribeOn(QuickExecution)
        .observeOn(AndroidSchedulers.mainThread())
}

fun Completable.applyIOScheduler(): Completable {
    return subscribeOn(LongExecution)
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.applyComputationScheduler(): Single<T> {
    return subscribeOn(QuickExecution)
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.applyIOScheduler(): Single<T> {
    return subscribeOn(LongExecution)
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.applyComputationScheduler(): Observable<T> {
    return subscribeOn(QuickExecution)
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.applyIOScheduler(): Observable<T> {
    return subscribeOn(LongExecution)
        .observeOn(AndroidSchedulers.mainThread())
}
