package com.github.lion4ik.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.lion4ik.R
import com.github.lion4ik.core.model.Forecast
import kotlinx.android.synthetic.main.list_item_forecast.view.*

class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    private val items: MutableList<Forecast> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_forecast, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ForecastViewHolder, position: Int) = viewHolder.bindData(items[position])

    override fun getItemCount(): Int = items.size

    operator fun plusAssign(newItem: Forecast) {
        items.add(newItem)
        notifyItemInserted(items.size - 1)
    }

    fun addList(newList: List<Forecast>) {
        val oldLastPos = items.size - 1
        items.addAll(newList)
        notifyItemRangeInserted(oldLastPos, newList.size)
    }

    class ForecastViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindData(item: Forecast) {
            itemView.apply {
                forecastTitle.text = item.timezone
                forecastTemperature.text = item.currently.temperature.toString()
                forecastLocation.text = "${item.latitude},${item.longitude}"
                forecastSummary.text = item.currently.summary
                forecastDetails.text = item.currently.summary
                humidity.text = item.currently.humidity.toString()
                windSpeed.text = item.currently.windSpeed.toString()
            }
        }
    }
}