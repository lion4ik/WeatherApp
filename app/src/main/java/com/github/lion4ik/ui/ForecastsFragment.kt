package com.github.lion4ik.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.github.lion4ik.App
import com.github.lion4ik.R
import com.github.lion4ik.ui.base.BaseFragment
import com.github.lion4ik.util.lazyNotThreadSafe
import com.github.lion4ik.util.nonNullObserve
import com.github.lion4ik.util.showSnackBar
import com.github.lion4ik.viewmodel.AddLocationViewModel
import com.github.lion4ik.viewmodel.ForecastsViewModel
import com.github.lion4ik.viewmodel.MainActivityViewModelFactory
import kotlinx.android.synthetic.main.element_toolbar.*
import kotlinx.android.synthetic.main.fragment_forecast.*
import timber.log.Timber
import javax.inject.Inject

class ForecastsFragment : BaseFragment() {

    companion object {
        fun newInstance(): ForecastsFragment = ForecastsFragment()
    }

    @Inject
    lateinit var viewModelFactory: MainActivityViewModelFactory

    private val forecastViewModel by lazyNotThreadSafe {
        ViewModelProviders.of(this, viewModelFactory).get(AddLocationViewModel::class.java)
    }

    private val forecastsViewModel by lazyNotThreadSafe {
        ViewModelProviders.of(this, viewModelFactory).get(ForecastsViewModel::class.java)
    }

    private val adapter by lazyNotThreadSafe { ForecastAdapter() }

    override fun getOptionalToolbar(): Toolbar? = toolbar

    override fun getLayoutRes(): Int = R.layout.fragment_forecast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addLocationFab.setOnClickListener {
            forecastViewModel.getLocationForecast(AddLocationViewModel.ForecastParams(51.500334, -0.085013))
        }
        initForecastList()
        observeData()
    }

    private fun initForecastList() {
        forecastList.layoutManager = LinearLayoutManager(context)
        forecastList.setHasFixedSize(true)

        forecastList.addItemDecoration(SimpleItemDecorator(resources.getDrawable(R.drawable.shape_divider, null)))
        forecastList.adapter = adapter
    }

    private fun observeData() {
        forecastsViewModel.getAllForecasts().nonNullObserve(this) {
            Timber.d("forecasts: " + it)
            adapter.addList(it)
        }
        forecastsViewModel.error.nonNullObserve(this) { showSnackBar(it) }
        forecastViewModel.getLocationForecast(AddLocationViewModel.ForecastParams(51.500334, -0.085013))
            .nonNullObserve(this) {
                Timber.d("forecast: " + it)
                adapter += it
            }
//
//        forecastViewModel.error.nonNullObserve(this) { showSnackBar(it) }
    }
}