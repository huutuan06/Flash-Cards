package com.flashcards.flashcards.ui.login

import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import com.flashcards.flashcards.BR
import com.flashcards.flashcards.R
import com.flashcards.flashcards.base.BaseFragment
import com.flashcards.flashcards.databinding.FragmentLoginBinding
import com.flashcards.flashcards.ui.MainActivity
import com.flashcards.flashcards.util.Constants
import com.flashcards.flashcards.util.FlashCardsAnalytics
import com.flashcards.flashcards.viewmodel.ViewModelProviderFactory
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(), View.OnClickListener {

    @Inject
    lateinit var mMainActivity: MainActivity

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var mFirebaseAnalytics: FirebaseAnalytics

    @Inject
    lateinit var mFlashCardsAnalytics: FlashCardsAnalytics

    private var mLoginViewModel: LoginViewModel? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }

    override fun initAttributes() {
        mFlashCardsAnalytics.reportScreen(mFirebaseAnalytics, mMainActivity, Constants.LOGIN_SCREEN)
        linear_login.setOnClickListener(this)
    }

    override fun getBindingVariable(): Int {
        return BR.loginViewModel
    }

    override fun getViewModel(): LoginViewModel {
        mLoginViewModel = ViewModelProvider(this, viewModelProviderFactory).get(LoginViewModel::class.java)
        return mLoginViewModel!!
    }

    fun login() {
        mMainActivity.mNavController.popBackStack()
        mMainActivity.mNavController.navigate(R.id.homeFragment)
    }

    override fun onClick(v: View?) {
        login()
    }
}
