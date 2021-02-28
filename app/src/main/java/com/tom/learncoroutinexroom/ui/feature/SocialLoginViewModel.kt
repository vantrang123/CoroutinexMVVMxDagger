package com.tom.learncoroutinexroom.ui.feature

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.tom.learncoroutinexroom.base.BaseViewModel
import javax.inject.Inject

class SocialLoginViewModel @Inject constructor(
        private val context: Context
) : BaseViewModel() {

    private val _isLoginSuccess = MutableLiveData<Boolean>()
    val isLoginSuccess: LiveData<Boolean> get() = _isLoginSuccess

    // facebook
    val faceBookLoginManager: LoginManager = LoginManager.getInstance()
    internal val mFacebookCallbackManager = CallbackManager.Factory.create()
    private val mFacebookCallback = object : FacebookCallback<LoginResult> {
        override fun onSuccess(result: LoginResult?) {
            val credential = FacebookAuthProvider.getCredential(result?.accessToken?.token!!)
            handleFacebookCredential(credential)
            _isLoginSuccess.postValue(true)
        }

        override fun onCancel() {
            _isLoginSuccess.postValue(false)
        }

        override fun onError(error: FacebookException?) {
            _isLoginSuccess.postValue(false)
        }

    }

    init {
        faceBookLoginManager.registerCallback(mFacebookCallbackManager, mFacebookCallback)
    }

    private fun handleFacebookCredential(authCredential: AuthCredential) {}

    // google
    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

    private val mGoogleSignClient by lazy {
        GoogleSignIn.getClient(context, gso)
    }

    fun googleSignIn() = mGoogleSignClient.signInIntent

    fun handleGoogleSignInResult(data: Intent) {
        val account = GoogleSignIn.getSignedInAccountFromIntent(data)
        _isLoginSuccess.postValue(true)
    }
}