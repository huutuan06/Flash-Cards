package com.flashcards.flashcards.ui.login

import androidx.lifecycle.ViewModelProvider
import com.flashcards.flashcards.BR
import com.flashcards.flashcards.R
import com.flashcards.flashcards.base.BaseFragment
import com.flashcards.flashcards.databinding.FragmentLoginBinding
import com.flashcards.flashcards.ui.main.MainActivity
import com.flashcards.flashcards.viewmodel.ViewModelProviderFactory
import javax.inject.Inject

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    @Inject
    lateinit var mMainActivity: MainActivity

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    override fun getLayoutId() = R.layout.fragment_login

    override fun getBindingVariable(): Int = BR.loginViewModel

    override fun getViewModel(): LoginViewModel =
        ViewModelProvider(this, viewModelProviderFactory).get(LoginViewModel::class.java)
}
