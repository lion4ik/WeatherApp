package com.github.lion4ik.util

import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment

fun Fragment.showSnackBar(@StringRes messageId: Int) {
    view?.run {
        val snackBar = Snackbar.make(this, messageId, Snackbar.LENGTH_LONG)
        snackBar.show()
    }
}