package com.github.lion4ik.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import com.github.lion4ik.App
import com.github.lion4ik.R
import com.github.lion4ik.ui.base.BaseFragment
import com.github.lion4ik.util.lazyNotThreadSafe
import com.github.lion4ik.util.nonNullObserve
import com.github.lion4ik.util.showSnackBar
import com.github.lion4ik.viewmodel.ForecastsViewModel
import com.github.lion4ik.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.element_toolbar.*
import kotlinx.android.synthetic.main.fragment_forecast.*
import javax.inject.Inject


class ForecastsFragment : BaseFragment() {

    companion object {
        fun newInstance(): ForecastsFragment = ForecastsFragment()
    }

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

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
            forecastsViewModel.onAddLocationClicked()
        }
        initForecastList()
        observeData()
    }

    private fun initForecastList() {
        forecastList.layoutManager = LinearLayoutManager(context)
        forecastList.setHasFixedSize(true)
        forecastList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        forecastList.adapter = adapter
        forecastList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0 && addLocationFab.isShown)
                    addLocationFab.hide()
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    addLocationFab.show()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    private fun observeData() {
        forecastsViewModel.getAllForecasts().nonNullObserve(this) { adapter.submitList(it) }
        forecastsViewModel.error.nonNullObserve(this) { showSnackBar(it) }
        forecastsViewModel.isEmpty.nonNullObserve(this) { isEmpty ->
            setEmptyVisible(isEmpty)
        }
    }

    private fun setEmptyVisible(isVisible: Boolean) {
        emptyList.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}