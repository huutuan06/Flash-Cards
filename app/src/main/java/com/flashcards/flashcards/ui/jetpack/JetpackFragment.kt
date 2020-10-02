package com.flashcards.flashcards.ui.jetpack

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.flashcards.flashcards.R
import com.flashcards.flashcards.base.BaseFragment
import com.flashcards.flashcards.databinding.FragmentJetpackBinding
import com.flashcards.flashcards.ui.view.LoadingDialog
import com.flashcards.flashcards.viewmodel.ViewModelProviderFactory
import kotlinx.coroutines.*
import javax.inject.Inject

class JetpackFragment : BaseFragment<FragmentJetpackBinding, JetpackViewModel>() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var loadingDialog: LoadingDialog

    override fun getLayoutId(): Int = R.layout.fragment_jetpack

    override fun getViewModel(): JetpackViewModel =
        ViewModelProvider(this, viewModelProviderFactory).get(JetpackViewModel::class.java)

    override fun initViews() {
        super.initViews()

        mViewModel.isLoading.observe(this, Observer {
            if (it) {
                loadingDialog.show()
            } else {
                loadingDialog.dismiss()
            }
        })
    }

    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.Main).launch {
            mViewModel.getRandomVocabularyFromServer()
        }
    }
}
