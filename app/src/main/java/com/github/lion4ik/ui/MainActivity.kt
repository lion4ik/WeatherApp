package com.github.lion4ik.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.github.lion4ik.App
import com.github.lion4ik.R
import com.github.lion4ik.navigation.ForecastScreen
import com.github.lion4ik.navigation.MainActivityNavigator
import com.github.lion4ik.ui.base.BaseActivity
import com.github.lion4ik.util.lazyNotThreadSafe
import com.github.lion4ik.util.nonNullObserve
import com.github.lion4ik.viewmodel.MainViewModelFactory
import com.github.lion4ik.viewmodel.ToolbarBackButtonViewModel
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val toolbarBackButtonViewModel by lazyNotThreadSafe {
        ViewModelProviders.of(this, viewModelFactory).get(ToolbarBackButtonViewModel::class.java)
    }

    private val navigator by lazyNotThreadSafe { MainActivityNavigator(this, R.id.container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            navigator.applyCommands(arrayOf<Command>(Replace(ForecastScreen())))
        }
        observeData()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    private fun observeData() {
        toolbarBackButtonViewModel.showToolbarBackButton.nonNullObserve(this) { isVisible ->
            supportActionBar?.run {
                setDisplayShowHomeEnabled(isVisible)
                setDisplayHomeAsUpEnabled(isVisible)
            }
        }
    }
}
