package com.github.lion4ik.navigation

import android.support.v4.app.Fragment
import com.github.lion4ik.ui.ForecastFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class ForecastScreen : SupportAppScreen() {

    override fun getFragment(): Fragment = ForecastFragment.newInstance()
}