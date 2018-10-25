package com.github.lion4ik.ui.base

import android.support.v7.widget.Toolbar

interface ToolbarAttacher {

    fun attach(toolbar: Toolbar?)

    fun detach()
}
