package com.github.lion4ik.ui

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.lion4ik.R
import com.github.lion4ik.core.model.Forecast
import kotlinx.android.synthetic.main.list_item_forecast.view.*
import java.text.DecimalFormat

class ForecastAdapter : ListAdapter<Forecast, ForecastAdapter.ForecastViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Forecast>() {

            override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Forecast, newItem: Forecast) = true
        }

        private val speedFormat = DecimalFormat("#.##")
        private val temperatureFormat = DecimalFormat("#.#")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_forecast, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ForecastViewHolder, position: Int) =
        viewHolder.bindData(getItem(position))

    class ForecastViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindData(item: Forecast) {
            itemView.apply {
                forecastTitle.text = item.timezone
                forecastTemperature.text =
                        context.getString(
                            R.string.fragment_forecasts_temperature_units,
                            temperatureFormat.format(item.temperature)
                        )
                forecastLocation.text = "${item.latitude}, ${item.longitude}"
                forecastSummary.text = item.summary
                forecastDetails.text = item.nearestForecastSummary
                humidity.text = item.humidity.toString()
                windSpeed.text = context.getString(
                    R.string.fragment_forecasts_speed_units,
                    speedFormat.format(item.windSpeed)
                )
            }
        }
    }
}