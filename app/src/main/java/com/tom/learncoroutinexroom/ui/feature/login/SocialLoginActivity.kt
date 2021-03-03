package com.tom.learncoroutinexroom.ui.feature.login

import android.content.Intent
import androidx.lifecycle.Observer
import com.tom.learncoroutinexroom.R
import com.tom.learncoroutinexroom.base.BaseActivity
import com.tom.learncoroutinexroom.databinding.ActivitySocialLoginBinding
import com.tom.learncoroutinexroom.di.injectViewModel
import com.tom.learncoroutinexroom.extensions.lauchActivity
import com.tom.learncoroutinexroom.ui.main.MainActivity


class SocialLoginActivity : BaseActivity<ActivitySocialLoginBinding, SocialLoginViewModel>() {
    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }

    override fun getViewModelClass(): Class<SocialLoginViewModel> = SocialLoginViewModel::class.java


    override fun initView() {
        binding.apply {
            // button facebook
            socialFacebookButton.setOnClickListener {
                viewModel.faceBookLoginManager.logInWithReadPermissions(this@SocialLoginActivity, facebook_permissions)
            }

            // button google
            socialGoogleButton.setOnClickListener {
                viewModel.googleSignIn().also {
                    startActivityForResult(it, RC_GOOGLE_SIGN_IN_CODE)
                }
            }
        }
        initViewModel()
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.apply {
            isLoginSuccess.observe(this@SocialLoginActivity, Observer {
                if (it) {
                    lauchActivity<MainActivity> {}
                    finish()
                } else snackBar("Login fail!")
            })
        }
    }

    override fun getLayoutResourceId() = R.layout.activity_social_login

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_GOOGLE_SIGN_IN_CODE && data != null) {
            viewModel.handleGoogleSignInResult(data)
        } else {
            viewModel.mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private val facebook_permissions = mutableListOf("email", "public_profile", "user_status")
        const val RC_GOOGLE_SIGN_IN_CODE = 2555
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}