package com.github.lion4ik.ui.base

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem

abstract class BaseActivity : AppCompatActivity(), ToolbarAttacher {

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun attach(toolbar: Toolbar?) {
        setSupportActionBar(toolbar)
    }

    override fun detach() {
        setSupportActionBar(null)
    }
}
