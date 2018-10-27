package com.github.lion4ik.navigation

import android.support.v4.app.Fragment
import com.github.lion4ik.ui.AddLocationFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class AddLocationScreen: SupportAppScreen() {

    override fun getFragment(): Fragment = AddLocationFragment.newInstance()
}