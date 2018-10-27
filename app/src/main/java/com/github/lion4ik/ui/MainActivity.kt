package com.github.lion4ik.ui

import android.os.Bundle
import com.github.lion4ik.App
import com.github.lion4ik.R
import com.github.lion4ik.navigation.ForecastScreen
import com.github.lion4ik.navigation.MainActivityNavigator
import com.github.lion4ik.ui.base.BaseActivity
import com.github.lion4ik.util.lazyNotThreadSafe
import com.github.lion4ik.viewmodel.MainActivityViewModelFactory
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: MainActivityViewModelFactory

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator by lazyNotThreadSafe { MainActivityNavigator(this, R.id.container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            navigator.applyCommands(arrayOf<Command>(Replace(ForecastScreen())))
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}
