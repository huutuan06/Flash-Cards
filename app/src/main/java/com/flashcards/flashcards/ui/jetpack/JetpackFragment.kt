package com.flashcards.flashcards.ui.jetpack

import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.flashcards.flashcards.R
import com.flashcards.flashcards.base.BaseFragment
import com.flashcards.flashcards.databinding.FragmentJetpackBinding
import com.flashcards.flashcards.ui.view.LoadingDialog
import com.flashcards.flashcards.util.preference.SettingPreference
import kotlinx.android.synthetic.main.fragment_jetpack.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.KClass

class JetpackFragment : BaseFragment<FragmentJetpackBinding, JetpackViewModel>() {

    @Inject
    lateinit var loadingDialog: LoadingDialog

    override val layoutId: Int
        get() = R.layout.fragment_jetpack

    override val viewModelClass: KClass<JetpackViewModel>
        get() = JetpackViewModel::class

    override fun initViews() {
        super.initViews()

        mViewModel.isLoading.observe(this, Observer {
            if (it) {
                loadingDialog.show()
            } else {
                loadingDialog.dismiss()
            }
        })

        mViewModel.randomVoca.observe(this, Observer {
            if (it != null) {
                Glide.with(this).load(it.image).into(img_voca)
            }
        })

        compositeDisposable.add(mViewModel.observableAction.subscribe {
            when (it) {
                is JetpackViewModel.Event.Error -> {
                    handleException(it.throwable)
                }
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
