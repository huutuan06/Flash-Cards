package com.flashcards.flashcards.ui.login

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.flashcards.flashcards.BR
import com.flashcards.flashcards.R
import com.flashcards.flashcards.databinding.FragmentLoginBinding
import com.flashcards.flashcards.base.BaseFragment
import com.flashcards.flashcards.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    @Inject
    lateinit var mainActivity: MainActivity

    private var mLoginViewModel: LoginViewModel? = null

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

    override fun getViewModel(): LoginViewModel {
        mLoginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        return mLoginViewModel!!
    }
}
