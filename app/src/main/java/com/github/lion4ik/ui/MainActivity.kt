package com.github.lion4ik.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.github.lion4ik.App
import com.github.lion4ik.R
import com.github.lion4ik.ui.base.BaseActivity
import com.github.lion4ik.util.lazyNotThreadSafe
import com.github.lion4ik.util.nonNullObserve
import com.github.lion4ik.viewmodel.ForecastViewModel
import com.github.lion4ik.viewmodel.MainActivityViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: MainActivityViewModelFactory

    private val forecastViewModel by lazyNotThreadSafe {
        ViewModelProviders.of(this, viewModelFactory).get(ForecastViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        setContentView(R.layout.activity_main)
        observeData()
        textView.setOnClickListener {
            forecastViewModel.getForecast(ForecastViewModel.ForecastParams(51.500334, -0.085013))
        }
    }

    private fun observeData() {
        forecastViewModel.getForecast(ForecastViewModel.ForecastParams(51.500334, -0.085013)).nonNullObserve(this) {
            Timber.d("forecast: " + it)
            textView.text = it.toString()
        }
    }
}
