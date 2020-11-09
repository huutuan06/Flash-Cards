package com.flashcards.flashcards.base

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.flashcards.flashcards.BR
import com.flashcards.flashcards.R
import com.flashcards.flashcards.util.FcException
import com.flashcards.flashcards.util.transform
import com.flashcards.flashcards.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private var bindingVariable : Int = BR.viewModel

    /**
     *@return layout resource id
     */
    @get:LayoutRes
    protected abstract val layoutId: Int
    protected abstract val viewModelClass: KClass<V>

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    protected val binding: T
        get() = _binding!!
    private var _binding: T? = null

    protected lateinit var mViewModel: V

    protected val compositeDisposable = CompositeDisposable()

    protected val mHandler = Handler(Looper.myLooper() ?: Looper.getMainLooper())

    private fun fragmentTag(): String = this.javaClass.simpleName

    //region lifecycle
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.tag("LifeCycle").d("${fragmentTag()} -- onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag("LifeCycle").d("${fragmentTag()} -- onCreate")

        mViewModel = ViewModelProvider(this ,viewModelProviderFactory).get(viewModelClass.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.setVariable(bindingVariable, mViewModel)
        binding.lifecycleOwner = this

        initViews()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag("LifeCycle").d("${fragmentTag()} -- onViewCreated")
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        Timber.tag("LifeCycle").d("${fragmentTag()} -- onCreateOptionsMenu")
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        Timber.tag("LifeCycle").d("${fragmentTag()} -- onPrepareOptionsMenu")
    }

    override fun onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu()
        Timber.tag("LifeCycle").d("${fragmentTag()} -- onDestroyOptionsMenu")
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
                toast(R.string.no_internet_error)
            }
            FcException.NoConnectivityError.ServerError -> {
                toast(R.string.server_error)
            }
            FcException.SessionExpiredError -> {
                toast(R.string.session_expired)
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

    open fun initViews() {}
}
