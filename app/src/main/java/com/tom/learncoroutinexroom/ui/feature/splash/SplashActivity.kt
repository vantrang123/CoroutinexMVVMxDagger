package com.tom.learncoroutinexroom.ui.feature.splash

import android.os.Handler
import android.os.Looper
import com.tom.learncoroutinexroom.R
import com.tom.learncoroutinexroom.base.BaseActivity
import com.tom.learncoroutinexroom.databinding.ActivitySplashBinding
import com.tom.learncoroutinexroom.di.injectViewModel
import com.tom.learncoroutinexroom.extensions.lauchActivity
import com.tom.learncoroutinexroom.ui.feature.login.SocialLoginActivity

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {
    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }

    override fun getViewModelClass(): Class<SplashViewModel> = SplashViewModel::class.java

    override fun initView() {
        binding.value = getString(R.string.app_name)

        Handler(Looper.getMainLooper()).postDelayed({
            lauchActivity<SocialLoginActivity> { }
        }, 2500)
    }

    override fun getLayoutResourceId(): Int = R.layout.activity_splash
}