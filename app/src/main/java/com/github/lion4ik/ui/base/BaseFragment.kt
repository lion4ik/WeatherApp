package com.github.lion4ik.ui.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : Fragment() {

    private var toolbarAttacher: ToolbarAttacher? = null
    protected abstract fun getOptionalToolbar(): Toolbar?

    @Suppress("UNCHECKED_CAST")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            toolbarAttacher = context as ToolbarAttacher
        } catch (e: ClassCastException) {
            throw IllegalArgumentException(" Activity for " + javaClass.name + " should implement ToolbarAttacher")
        }
    }

    override fun onStart() {
        super.onStart()
        onBeforeAttachToolbar(getOptionalToolbar())
        toolbarAttacher?.attach(getOptionalToolbar())
        onToolbarAttached(getOptionalToolbar())
    }

    override fun onStop() {
        super.onStop()
        toolbarAttacher?.detach()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = inflater.inflate(getLayoutRes(), container, false)

    open fun onBackPressed(): Boolean = false

    @LayoutRes
    abstract fun getLayoutRes(): Int

    protected open fun onBeforeAttachToolbar(toolbar: Toolbar?) {}

    protected open fun onToolbarAttached(toolbar: Toolbar?) {}
}
