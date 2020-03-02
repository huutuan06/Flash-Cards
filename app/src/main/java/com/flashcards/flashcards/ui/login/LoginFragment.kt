package com.flashcards.flashcards.ui.login

import android.widget.Toast
import com.flashcards.flashcards.BR
import com.flashcards.flashcards.R
import com.flashcards.flashcards.databinding.FragmentLoginBinding
import com.flashcards.flashcards.base.BaseFragment
import com.flashcards.flashcards.ui.MainActivity
import javax.inject.Inject

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    @Inject
    lateinit var mainActivity: MainActivity

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }

    override fun initAttributes() {
    }

    fun onLoginClick() {
        Toast.makeText(context, "Login Button", Toast.LENGTH_SHORT).show()
    }

    override fun getBindingVariable(): Int {
        return BR.loginViewModel
    }
}
