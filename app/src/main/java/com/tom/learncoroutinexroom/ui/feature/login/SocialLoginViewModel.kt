package com.tom.learncoroutinexroom.ui.feature.login

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.tom.learncoroutinexroom.base.BaseViewModel
import com.tom.learncoroutinexroom.common.Result
import com.tom.learncoroutinexroom.extensions.await
import com.tom.learncoroutinexroom.extensions.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class SocialLoginViewModel @Inject constructor(
        private val context: Context,
        private val firebaseAuth: FirebaseAuth,
        @Named("IO") private val io: CoroutineDispatcher,
        @Named("MAIN") private val main: CoroutineDispatcher
) : BaseViewModel() {

    private val _isLoginSuccess = MutableLiveData<Boolean>()
    val isLoginSuccess: LiveData<Boolean> get() = _isLoginSuccess

    // facebook
    val faceBookLoginManager: LoginManager = LoginManager.getInstance()
    internal val mFacebookCallbackManager = CallbackManager.Factory.create()
    private val mFacebookCallback = object : FacebookCallback<LoginResult> {
        override fun onSuccess(result: LoginResult?) {
            _isLoginSuccess.postValue(result?.accessToken != null)
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

    // google
    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

    private val mGoogleSignClient by lazy {
        GoogleSignIn.getClient(context, gso)
    }

    fun googleSignIn() = mGoogleSignClient.signInIntent

    fun handleGoogleSignInResult(data: Intent) {
        viewModelScope.launch {
            val result = async(context = io) {
                GoogleSignIn.getSignedInAccountFromIntent(data)
            }
            _isLoginSuccess.postValue(result.await().getResult(ApiException::class.java) != null)
        }
    }
}