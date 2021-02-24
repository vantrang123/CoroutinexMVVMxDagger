package com.tom.learncoroutinexroom.data.model.error

import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

class ErrorMessage(private val throwable: Throwable? = null, var message: String = "", val isSpecialCase: Boolean = false, body: Any? = null) {
    var error: CommonError = CommonError.UNKNOWN

    init {
        throwable?.let {
            when (throwable) {
                is HttpException -> setResponseError(throwable.response()!!)
//                is NoConnectionException -> setError(NETWORK_ERROR)
                else -> onApiFailure(throwable)
            }
        }
    }

    private fun setResponseError(response: Response<*>) {
        setError(response.code())
        try {
            val jObjError = JSONObject(response.errorBody()!!.string())
            message = jObjError.getString("message")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun onApiFailure(error: Throwable) {
        if (error is UnknownError || error is UnknownHostException) {
            setError(NETWORK_ERROR)
        } else {
            setError(SERVER_ERROR)
        }
    }

    private fun setError(code: Int) {
        when (code) {
            NETWORK_ERROR -> error = CommonError.NETWORK_ERROR
            TOKEN_EXPIRED -> error = CommonError.UNAUTHENTICATED
            BAD_REQUEST -> error = CommonError.BAD_REQUEST
            SERVER_ERROR -> error = CommonError.SERVER_ERROR
            RESULT_ERROR -> error = CommonError.RESULT_ERROR
            PROMO_CODE_ERROR -> error = CommonError.PROMO_CODE_ERROR
            SERVER_MAINTENANCE -> error = CommonError.SERVER_MAINTENANCE
        }
    }

    companion object {
        const val TOKEN_EXPIRED = 401
        const val BAD_REQUEST = 400
        const val SERVER_ERROR = 500
        const val RESULT_ERROR = 404
        const val NETWORK_ERROR = 1
        const val PROMO_CODE_ERROR = 406
        const val SERVER_MAINTENANCE = 999
    }
}