package com.github.lion4ik.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.github.lion4ik.App
import com.github.lion4ik.R
import com.github.lion4ik.ui.base.BaseFragment
import com.github.lion4ik.util.lazyNotThreadSafe
import com.github.lion4ik.util.nonNullObserve
import com.github.lion4ik.util.showSnackBar
import com.github.lion4ik.viewmodel.ForecastViewModel
import com.github.lion4ik.viewmodel.MainActivityViewModelFactory
import kotlinx.android.synthetic.main.element_toolbar.*
import kotlinx.android.synthetic.main.fragment_forecast.*
import timber.log.Timber
import javax.inject.Inject

class ForecastFragment : BaseFragment() {

    companion object {
        fun newInstance(): ForecastFragment = ForecastFragment()
    }

    @Inject
    lateinit var viewModelFactory: MainActivityViewModelFactory

    private val forecastViewModel by lazyNotThreadSafe {
        ViewModelProviders.of(this, viewModelFactory).get(ForecastViewModel::class.java)
    }

    override fun getOptionalToolbar(): Toolbar? = toolbar

    override fun getLayoutRes(): Int = R.layout.fragment_forecast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView.setOnClickListener {
            forecastViewModel.getForecast(ForecastViewModel.ForecastParams(51.500334, -0.085013))
        }
        observeData()
    }

    private fun observeData() {
        forecastViewModel.getForecast(ForecastViewModel.ForecastParams(51.500334, -0.085013)).nonNullObserve(this) {
            Timber.d("forecast: " + it)
            textView.text = it.toString()
        }

        forecastViewModel.error.nonNullObserve(this) { showSnackBar(it) }
    }
}