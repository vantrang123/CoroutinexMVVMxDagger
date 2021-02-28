package com.tom.learncoroutinexroom.ui.feature

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
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.*
import com.tom.learncoroutinexroom.base.BaseViewModel
import com.tom.learncoroutinexroom.common.Result
import com.tom.learncoroutinexroom.extensions.await
import com.tom.learncoroutinexroom.extensions.safeApiCall
import kotlinx.coroutines.launch
import javax.inject.Inject

class SocialLoginViewModel @Inject constructor(
        private val context: Context,
        private val firebaseAuth: FirebaseAuth
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

    private fun handleFacebookCredential(authCredential: AuthCredential) {
        viewModelScope.launch {
            safeApiCall { Result.success(signInWithCredential(authCredential)!!) }.also {
                if (it.status == Result.Status.SUCCESS && it.data?.user != null) {
                    _isLoginSuccess.postValue(true)
                }
                _isLoginSuccess.postValue(true)
            }
        }
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
            safeApiCall {
                val account = GoogleSignIn.getSignedInAccountFromIntent(data).await()
                val authResult =
                        signInWithCredential(GoogleAuthProvider.getCredential(account.idToken, null))!!
                Result.success(authResult)
            }.also {
                if (it.status == Result.Status.SUCCESS && it.data?.user != null) {
                    _isLoginSuccess.postValue(true)
                }
                _isLoginSuccess.postValue(true)
            }
        }
    }

    @Throws(Exception::class)
    private suspend fun signInWithCredential(authCredential: AuthCredential): AuthResult? {
        return firebaseAuth.signInWithCredential(authCredential).await()
    }
}