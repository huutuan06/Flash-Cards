package com.flashcards.flashcards.ui.login

import androidx.lifecycle.ViewModelProviders
import com.flashcards.flashcards.BR
import com.flashcards.flashcards.R
import com.flashcards.flashcards.base.BaseFragment
import com.flashcards.flashcards.databinding.FragmentLoginBinding
import com.flashcards.flashcards.ui.MainActivity
import com.flashcards.flashcards.viewmodel.ViewModelProviderFactory
import javax.inject.Inject

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(), ILogin {

    @Inject
    lateinit var mMainActivity: MainActivity

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private var mLoginViewModel: LoginViewModel? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }

    override fun initAttributes() {
        mLoginViewModel!!.setInterface(this)
    }

    override fun getBindingVariable(): Int {
        return BR.loginViewModel
    }

    override fun getViewModel(): LoginViewModel {
        mLoginViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(LoginViewModel::class.java)
        return mLoginViewModel!!
    }

    override fun login() {
        mMainActivity.mNavController.popBackStack()
        mMainActivity.mNavController.navigate(R.id.homeFragment)
    }

}
