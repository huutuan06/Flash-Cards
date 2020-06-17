package com.flashcards.flashcards.util

import retrofit2.HttpException
import java.net.SocketException
import java.net.UnknownHostException

sealed class FcException : Throwable() {

    override val message: String?
        get() = this::class.java.simpleName

    sealed class NoConnectivityError : FcException(){
        object DeviceError : NoConnectivityError()
        object ServerError : NoConnectivityError()
    }

    object SessionExpiredError : FcException()

    data class GenericError(val throwable: Throwable) : FcException()
}

fun Throwable.transform() : FcException {
    if (this is FcException) {
        return this
    }

    return when (this) {
        is UnknownHostException,
            is InterruptedException -> FcException.NoConnectivityError.DeviceError
        is HttpException -> {
            if (code() == 401) {
                FcException.SessionExpiredError
            } else {
                FcException.NoConnectivityError.ServerError
            }
        }
        is SocketException -> FcException.NoConnectivityError.ServerError
        else -> FcException.GenericError(this)
    }
}