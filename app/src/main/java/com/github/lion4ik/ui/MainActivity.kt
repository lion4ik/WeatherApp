package com.github.lion4ik.ui

import android.os.Bundle
import com.github.lion4ik.R
import com.github.lion4ik.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
