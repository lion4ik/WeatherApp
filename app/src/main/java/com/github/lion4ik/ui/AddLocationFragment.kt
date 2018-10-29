package com.github.lion4ik.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.github.lion4ik.App
import com.github.lion4ik.R
import com.github.lion4ik.core.model.Forecast
import com.github.lion4ik.ui.base.BaseFragment
import com.github.lion4ik.util.lazyNotThreadSafe
import com.github.lion4ik.util.nonNullObserve
import com.github.lion4ik.util.showSnackBar
import com.github.lion4ik.viewmodel.AddLocationViewModel
import com.github.lion4ik.viewmodel.MainViewModelFactory
import com.github.lion4ik.viewmodel.ToolbarBackButtonViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.element_toolbar.*
import kotlinx.android.synthetic.main.fragment_add_location.*
import timber.log.Timber
import javax.inject.Inject

class AddLocationFragment : BaseFragment(), OnMapReadyCallback {

    companion object {
        fun newInstance(): AddLocationFragment = AddLocationFragment()
    }

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    private val toolbarBackButtonViewModel by lazyNotThreadSafe {
        activity?.run { ViewModelProviders.of(this, viewModelFactory).get(ToolbarBackButtonViewModel::class.java) }
            ?: throw IllegalArgumentException("Activity is needed to create ToolbarBackButtonViewModel")
    }

    private val addLocationViewModel by lazyNotThreadSafe {
        ViewModelProviders.of(this, viewModelFactory).get(AddLocationViewModel::class.java)
    }

    private var map: GoogleMap? = null
    private var marker: Marker? = null

    override fun getOptionalToolbar(): Toolbar? = toolbar

    override fun getLayoutRes(): Int = R.layout.fragment_add_location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationMap.onCreate(savedInstanceState)
        locationMap.getMapAsync(this)
        chooseLocationFab.setOnClickListener { addLocationViewModel.onSelectedLocation() }
        observeData()
    }

    override fun onBeforeAttachToolbar(toolbar: Toolbar?) {
        super.onBeforeAttachToolbar(toolbar)
        toolbar?.setTitle(R.string.fragment_add_location_title)
    }

    override fun onToolbarAttached(toolbar: Toolbar?) {
        super.onToolbarAttached(toolbar)
        toolbarBackButtonViewModel.setToolbarBackButtonVisibility(true)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        locationMap?.onSaveInstanceState(outState)
    }

    override fun onPause() {
        locationMap?.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        locationMap?.onResume()
    }

    override fun onStop() {
        locationMap?.onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        locationMap?.onStart()
    }

    override fun onDestroy() {
        locationMap?.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        locationMap?.onLowMemory()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map?.setOnMapClickListener {
            addLocationViewModel.onLocationClicked(it)
            addLocationViewModel.getLocationForecast(AddLocationViewModel.ForecastParams(it.latitude, it.longitude))
        }
    }

    private fun observeData() {
        addLocationViewModel.locationSelected.nonNullObserve(this) { latLng ->
            setMapMarker(latLng)
        }
        addLocationViewModel.forecastForCurrentLocation.nonNullObserve(this) {
            Timber.d("location " + it)
            setMapMarker(LatLng(it.latitude, it.longitude))
            showLocationInfo(it)
        }
        addLocationViewModel.forecastError.nonNullObserve(this) { showSnackBar(it) }
        addLocationViewModel.getLocationForecast(AddLocationViewModel.DEFAULT_LOCATION)
    }

    private fun showLocationInfo(forecast: Forecast) {
        locationTitle.text = forecast.timezone
        locationPosition.text = "${forecast.latitude}, ${forecast.longitude}"
    }

    private fun setMapMarker(latLng: LatLng) {
        if (marker == null) {
            marker = map?.addMarker(MarkerOptions().position(latLng))
        } else {
            marker?.run { position = latLng }
        }
    }
}