package com.flashcards.flashcards.base

import android.content.Context
import android.os.Bundle
import android.os.Handler
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
import timber.log.Timber

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : DaggerFragment() {

    private lateinit var mRootView: View

    protected val binding: T
        get() = _binding!!
    private var _binding: T? = null

    protected lateinit var mViewModel: V

    protected val compositeDisposable = CompositeDisposable()

    protected val mHandler = Handler()

    private fun fragmentTag(): String =this.javaClass.simpleName

    //region lifecycle
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.tag("LifeCycle").d("${fragmentTag()} -- onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag("LifeCycle").d("${fragmentTag()} -- onCreate")

        mViewModel = getViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mRootView = binding.root
        initViews()
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag("LifeCycle").d("${fragmentTag()} -- onViewCreated")

        binding.setVariable(getBindingVariable(), mViewModel)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.tag("LifeCycle").d("${fragmentTag()} -- onActivityCreated")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Timber.tag("LifeCycle").d("${fragmentTag()} -- onViewStateRestored")
    }

    override fun onStart() {
        super.onStart()
        Timber.tag("LifeCycle").d("${fragmentTag()} -- onStart")
    }

    override fun onResume() {
        super.onResume()
        Timber.tag("LifeCycle").d("${fragmentTag()} -- onResume")
    }

    override fun onPause() {
        Timber.tag("LifeCycle").d("${fragmentTag()} -- onPause")
        super.onPause()
    }

    override fun onStop() {
        Timber.tag("LifeCycle").d("${fragmentTag()} -- onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Timber.tag("LifeCycle").d("${fragmentTag()} -- onDestroyView")

        mHandler.removeCallbacksAndMessages(null)

        super.onDestroyView()
    }

    override fun onDestroy() {
        Timber.tag("LifeCycle").d("${fragmentTag()} -- onDestroy")

        compositeDisposable.dispose()
        _binding = null

        super.onDestroy()
    }

    override fun onDetach() {
        Timber.tag("LifeCycle").d("${fragmentTag()} -- onDetach")
        super.onDetach()
    }
    //endregion

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
            else -> Timber.e("Cannot handle exception: ${throwable.printStackTrace()}")
        }
    }

    private var mToast: Toast? = null
    protected fun toast(resId: Int, duration: Int = Toast.LENGTH_SHORT) {
        mToast?.cancel()
        mToast = Toast.makeText(context, resId, duration)
        mToast?.show()
    }

    protected fun toast(message: String? = null, duration: Int = Toast.LENGTH_SHORT) {
        mToast?.cancel()
        message?.let {
            mToast = Toast.makeText(context, it, duration)
            mToast?.show()
        }
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getBindingVariable() : Int

    abstract fun getViewModel(): V

    open fun initViews() {}
}
