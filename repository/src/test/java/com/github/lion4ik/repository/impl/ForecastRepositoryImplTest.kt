package com.github.lion4ik.repository.impl

import com.github.lion4ik.core.error.ApiException
import com.github.lion4ik.core.model.Forecast
import com.github.lion4ik.core.remote.ForecastRemote
import com.github.lion4ik.core.storage.ForecastStorage
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ForecastRepositoryImplTest {

    @Mock
    private lateinit var forecast: Forecast

    private lateinit var forecastRepositoryImpl: ForecastRepositoryImpl

    private val lat = 51.500334
    private val long = -0.085013
    private val lang = "en"
    private val units = "si"

    private lateinit var forecastsList: List<Forecast>

    @Before
    fun setUp() {
        forecastsList = listOf(forecast, forecast)
    }

    @Test
    fun `when getAllForecasts then correct result returned`() = runBlocking {
        val remote: ForecastRemote = mock()
        val storage: ForecastStorage = mock {
            onBlocking {
                getAllForecasts()
            } doReturn forecastsList
        }
        forecastRepositoryImpl = ForecastRepositoryImpl(remote, storage)
        val list = forecastRepositoryImpl.getAllForecasts()
        assertEquals(forecastsList, list)
        verify(storage).getAllForecasts()
        verifyNoMoreInteractions(storage)
        verifyZeroInteractions(remote)
        Unit
    }

    @Test(expected = ApiException::class)
    fun `when getAllForecasts then error returned`() = runBlocking {
        val remote: ForecastRemote = mock()
        val storage: ForecastStorage = mock {
            onBlocking {
                getAllForecasts()
            } doThrow ApiException()
        }
        forecastRepositoryImpl = ForecastRepositoryImpl(remote, storage)
        forecastRepositoryImpl.getAllForecasts()
        Unit
    }

    @Test
    fun `when getForecastRemote then correct result returned`() = runBlocking {
        val remote: ForecastRemote = mock {
            onBlocking {
                getForecast(lat, long, lang, units)
            } doReturn forecast
        }

        val storage: ForecastStorage = mock()
        forecastRepositoryImpl = ForecastRepositoryImpl(remote, storage)
        val f = forecastRepositoryImpl.getForecastRemote(lat, long, lang, units)
        assertEquals(forecast, f)
        verify(remote).getForecast(lat, long, lang, units)
        verifyZeroInteractions(storage)
        verifyNoMoreInteractions(remote)
        Unit
    }

    @Test(expected = ApiException::class)
    fun `when getForecastRemote then error returned`() = runBlocking {
        val remote: ForecastRemote = mock {
            onBlocking {
                getForecast(lat, long, lang, units)
            } doThrow ApiException()
        }

        val storage: ForecastStorage = mock()
        forecastRepositoryImpl = ForecastRepositoryImpl(remote, storage)
        forecastRepositoryImpl.getForecastRemote(lat, long, lang, units)
        Unit
    }

    @Test
    fun `when addForecast then correct result returned`() = runBlocking {
        val remote: ForecastRemote = mock()
        val storage: ForecastStorage = mock {
            onBlocking {
                addForecast(forecast)
            } doReturn Unit
        }
        forecastRepositoryImpl = ForecastRepositoryImpl(remote, storage)
        forecastRepositoryImpl.addForecast(forecast)
        verify(storage).addForecast(forecast)
        verifyNoMoreInteractions(storage)
        verifyZeroInteractions(remote)
        Unit
    }

    @Test(expected = ApiException::class)
    fun `when addForecast then error returned`() = runBlocking {
        val remote: ForecastRemote = mock()
        val storage: ForecastStorage = mock {
            onBlocking {
                addForecast(forecast)
            } doThrow ApiException()
        }
        forecastRepositoryImpl = ForecastRepositoryImpl(remote, storage)
        forecastRepositoryImpl.addForecast(forecast)
        Unit
    }
}