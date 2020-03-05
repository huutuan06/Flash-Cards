package com.flashcards.flashcards.ui.login

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.flashcards.flashcards.BR
import com.flashcards.flashcards.R
import com.flashcards.flashcards.base.BaseFragment
import com.flashcards.flashcards.databinding.FragmentLoginBinding
import com.flashcards.flashcards.ui.MainActivity
import javax.inject.Inject

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(), ILogin {

    @Inject
    lateinit var mMainActivity: MainActivity

    @Inject
    lateinit var str: String

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
        mLoginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        return mLoginViewModel!!
    }

    override fun login() {
        Log.d("test",str)
        mMainActivity.mNavController.popBackStack()
        mMainActivity.mNavController.navigate(R.id.homeFragment)
    }

}
