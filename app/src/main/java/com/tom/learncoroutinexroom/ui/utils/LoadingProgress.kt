package com.tom.learncoroutinexroom.ui.utils

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.tom.learncoroutinexroom.R

class LoadingProgress(context: Context) : Dialog(context, R.style.ProgressDialog) {

    private var countLoading = 0

    init {
        initLoadingProgress(context)
    }

    private fun initLoadingProgress(context: Context) {
        this.setContentView(R.layout.loading_progress)
        this.setCancelable(false)
        this.setCanceledOnTouchOutside(false)
    }

    override fun show() {
        try {
            if (countLoading == 0) {
                super.show()
            }
            countLoading++
        } catch (e: Exception) {
        }
    }

    override fun dismiss() {
        countLoading--
        if (countLoading > 0 || !super.isShowing()) return
        super.dismiss()
    }

    fun forceDismiss() {
        countLoading = 0
        super.dismiss()
    }
}