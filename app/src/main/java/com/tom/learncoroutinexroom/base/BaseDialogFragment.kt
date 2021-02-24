package com.tom.learncoroutinexroom.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.tom.learncoroutinexroom.R
import com.tom.learncoroutinexroom.ui.utils.LoadingProgress
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

abstract class BaseDialogFragment<B : ViewDataBinding, V : BaseViewModel> : DaggerDialogFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mViewDataBinding: B
    protected lateinit var mViewModel: V
    private var loadingProgress: LoadingProgress? = null

    val binding: B get() = mViewDataBinding
    val viewModel: V get() = mViewModel

    abstract fun injectViewModel()
    abstract fun getViewModelClass(): Class<V>

    @LayoutRes
    abstract fun getLayoutResourceId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(STYLE_NORMAL, R.style.AppTheme_Fragment)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        dialog?.window?.attributes?.windowAnimations = R.style.AppTheme_Fragment
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        injectViewModel()
        mViewDataBinding =
            DataBindingUtil.inflate(inflater, getLayoutResourceId(), container, false)
        mViewDataBinding.lifecycleOwner = this
        mViewDataBinding.executePendingBindings()
        return mViewDataBinding.root
    }

    private fun snackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    open fun initView() {}
    open fun initViewModel() {
        viewModel.apply {
            error.observe(viewLifecycleOwner, Observer {
                snackBar(it.message)
            })

            isLoading.observe(viewLifecycleOwner, Observer {
                if (it) {
                    showLoadingDialog()
                } else {
                    dismissLoadingDialog()
                }
            })
        }
    }

    open fun showLoadingDialog() {
        if (loadingProgress == null) {
            loadingProgress = LoadingProgress(requireContext())
        }
        loadingProgress?.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }

    open fun dismissLoadingDialog() {
        try {
            loadingProgress?.let {
                it.dismiss()
                loadingProgress = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}