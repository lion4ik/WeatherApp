package com.github.lion4ik.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.github.lion4ik.core.error.ApiException
import com.github.lion4ik.core.model.Forecast
import com.github.lion4ik.domain.AddForecastUseCase
import com.github.lion4ik.domain.GetForecastUseCase
import com.google.android.gms.maps.model.LatLng
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import ru.terrakok.cicerone.Router

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class AddLocationViewModelTest {

    @get:Rule
    val mockito: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val testExecutor: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var router: Router

    @Mock
    private lateinit var forecast: Forecast

    @Mock
    private lateinit var forecastObserver: Observer<Forecast>

    @Mock
    private lateinit var locationSelectedObserver: Observer<LatLng>

    @Mock
    private lateinit var forecastErrorObserver: Observer<Int>

    private lateinit var viewModel: AddLocationViewModel

    private val defaultLocation = AddLocationViewModel.DEFAULT_LOCATION

    @Test
    fun `when getLocationForecast is called then correct result returned`() = runBlocking {
        val forecastUseCase: GetForecastUseCase = mock {
            onBlocking {
                execute(
                    defaultLocation.lat,
                    defaultLocation.long,
                    defaultLocation.lang,
                    defaultLocation.units
                )
            } doReturn forecast
        }

        val addForecastUseCase: AddForecastUseCase = mock {
            onBlocking {
                execute(forecast)
            } doReturn Unit
        }

        initViewModel(forecastUseCase, addForecastUseCase)

        viewModel.getLocationForecast(defaultLocation)

        verify(forecastObserver).onChanged(forecast)
        verifyZeroInteractions(forecastErrorObserver)

        deinitViewModel()
    }

    @Test
    fun `when getLocationForecast is called then error returned`() = runBlocking {
        val forecastUseCase: GetForecastUseCase = mock {
            onBlocking {
                execute(
                    defaultLocation.lat,
                    defaultLocation.long,
                    defaultLocation.lang,
                    defaultLocation.units
                )
            } doThrow ApiException()
        }

        val addForecastUseCase: AddForecastUseCase = mock {
            onBlocking {
                execute(forecast)
            } doReturn Unit
        }

        initViewModel(forecastUseCase, addForecastUseCase)

        viewModel.getLocationForecast(defaultLocation)

        verifyZeroInteractions(forecastObserver)
        verify(forecastErrorObserver).onChanged(any())

        deinitViewModel()
    }

    @Test
    fun `when onLocationClicked called then correct data passed to observer`() {
        val forecastUseCase: GetForecastUseCase = mock {
            onBlocking {
                execute(
                    defaultLocation.lat,
                    defaultLocation.long,
                    defaultLocation.lang,
                    defaultLocation.units
                )
            } doReturn forecast
        }

        val addForecastUseCase: AddForecastUseCase = mock {
            onBlocking {
                execute(forecast)
            } doReturn Unit
        }

        val latLng = LatLng(defaultLocation.lat, defaultLocation.long)
        initViewModel(forecastUseCase, addForecastUseCase)
        viewModel.locationSelected.observeForever(locationSelectedObserver)
        viewModel.onLocationClicked(latLng)
        verify(locationSelectedObserver).onChanged(latLng)

        deinitViewModel()
        viewModel.locationSelected.removeObserver(locationSelectedObserver)
    }

    @Test
    fun `when onSelectedLocation called then forecast saved and exit`() = runBlocking {
        val forecastUseCase: GetForecastUseCase = mock {
            onBlocking {
                execute(
                    defaultLocation.lat,
                    defaultLocation.long,
                    defaultLocation.lang,
                    defaultLocation.units
                )
            } doReturn forecast
        }

        val addForecastUseCase: AddForecastUseCase = mock {
            onBlocking {
                execute(forecast)
            } doReturn Unit
        }

        initViewModel(forecastUseCase, addForecastUseCase)

        viewModel.getLocationForecast(defaultLocation)

        verify(forecastObserver).onChanged(forecast)
        verifyZeroInteractions(forecastErrorObserver)

        viewModel.onSelectedLocation()
        verify(addForecastUseCase).execute(forecast)
        verify(router).exit()

        deinitViewModel()
    }

    private fun initViewModel(forecastUseCase: GetForecastUseCase, addForecastUseCase: AddForecastUseCase) {
        viewModel = AddLocationViewModel(router, forecastUseCase, addForecastUseCase)
        viewModel.forecastForCurrentLocation.observeForever(forecastObserver)
        viewModel.forecastError.observeForever(forecastErrorObserver)
    }

    private fun deinitViewModel() {
        viewModel.forecastForCurrentLocation.removeObserver(forecastObserver)
        viewModel.forecastError.removeObserver(forecastErrorObserver)
    }
}