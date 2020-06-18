package com.flashcards.flashcards.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.flashcards.flashcards.R
import com.flashcards.flashcards.util.FcException
import com.flashcards.flashcards.util.transform
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : DaggerFragment() {

    private lateinit var mRootView: View
    protected lateinit var binding: T
    protected lateinit var mViewModel: V
    protected val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mRootView = binding.root
        initView()
        return mRootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(getBindingVariable(), mViewModel)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    protected fun handleException(throwable: Throwable) {
        when (throwable.transform()) {
            FcException.NoConnectivityError.DeviceError -> {
                Toast.makeText(context, R.string.no_internet_error, Toast.LENGTH_LONG).show()
            }
            FcException.NoConnectivityError.ServerError -> {
                Toast.makeText(context, R.string.server_error, Toast.LENGTH_LONG).show()
            }
            FcException.SessionExpiredError -> {
                Toast.makeText(context, R.string.session_expired, Toast.LENGTH_LONG).show()
            }
//            FcException.GenericError -> {
//                Toast.makeText(context, throwable.message ?: "Exception occur. Please try again later", Toast.LENGTH_SHORT).show()
//            }
            else -> Log.e("Activity","Cannot handle exception: ${throwable.printStackTrace()}")
        }
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getBindingVariable() : Int

    abstract fun getViewModel(): V

    open fun initView() {}
}
