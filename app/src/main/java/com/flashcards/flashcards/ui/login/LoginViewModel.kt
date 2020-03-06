package com.flashcards.flashcards.ui.login

import com.flashcards.flashcards.base.BaseViewModel
import javax.inject.Inject

class LoginViewModel : BaseViewModel() {

    private lateinit var mLoginListener: ILogin

    fun onLoginClick() {
        mLoginListener.login()
    }

    fun setInterface(listener: ILogin) {
        mLoginListener = listener
    }

    @Inject
    fun LoginViewModel() {

    }
}