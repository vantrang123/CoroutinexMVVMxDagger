package com.tom.learncoroutinexroom.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.tom.learncoroutinexroom.R
import com.tom.learncoroutinexroom.ui.utils.LoadingProgress
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<B : ViewDataBinding, V : BaseViewModel> : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mViewDataBinding: B
    protected lateinit var mViewModel: V
    private var loadingProgress: LoadingProgress? = null

    val binding: B get() = mViewDataBinding
    val viewModel: V get() = mViewModel

    abstract fun injectViewModel()
    abstract fun getViewModelClass(): Class<V>
    abstract fun initView()

    @LayoutRes
    abstract fun getLayoutResourceId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        injectViewModel()
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutResourceId())
        initView()
    }

    open fun initViewModel() {
        viewModel.apply {
            error.observe(this@BaseActivity, Observer {
                snackBar(it.message)
            })

            isLoading.observe(this@BaseActivity, Observer {
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
            loadingProgress = LoadingProgress(this)
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

    protected fun snackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}