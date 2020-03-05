package com.flashcards.flashcards.ui.login

import com.flashcards.flashcards.base.BaseViewModel

class LoginViewModel : BaseViewModel() {

    private lateinit var mLoginListener: ILogin

    fun onLoginClick() {
        mLoginListener.login()
    }

    fun setInterface(listener: ILogin) {
        mLoginListener = listener
    }
}