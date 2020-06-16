package com.flashcards.flashcards.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.view.Window
import com.flashcards.flashcards.R

class LoadingDialog(private val activity: Activity) : Dialog(activity) {

    init {
        init()
    }

    private fun init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawableResource(R.color.transparent)
        setContentView(R.layout.progress_dialog)
        setCancelable(false)
    }

    override fun show() {
        if (!activity.isFinishing) {
            super.show()
        }
    }

    override fun dismiss() {
        if (!activity.isFinishing) {
            super.dismiss()
        }
    }
}
